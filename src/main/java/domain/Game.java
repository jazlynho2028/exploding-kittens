package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static domain.DeckBuilder.createCardId;

public class Game {

    private final List<Player> players;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    private boolean isGameOngoing;
    private boolean isFaceUp;

    @SuppressFBWarnings(
            value = {"EI_EXPOSE_REP2"},
            justification = "EI_EXPOSE_REP2: players, drawPile, discardPile, and turnManager " +
                    "are injected by for testability and coverage. Defensive copy is not " +
                    "desired in this context."
    )
    public Game(List<Player> players, Deck drawPile,
                Deck discardPile, TurnManager turnManager) {

        this.players = players;
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.turnManager = turnManager;

        isGameOngoing = false;
        isFaceUp = false;
    }

    public void setUp() {
        int numPlayers = players.size();
        verifyMinPlayers(numPlayers);
        verifyMaxPlayers(numPlayers);
        populatePlayerHands();
    }

    private void verifyMinPlayers(int numPlayers) {
        if (numPlayers < GameConstants.MIN_PLAYERS) {
            throw new IllegalArgumentException("error.minPlayers");
        }
    }

    private void verifyMaxPlayers(int numPlayers) {
        if (numPlayers > GameConstants.MAX_PLAYERS) {
            throw new IllegalArgumentException("error.maxPlayers");
        }
    }

    private void populatePlayerHands() {
        populateHandsWithDefuse();

        populateHandsWithNonDefuseStartingCards();
    }

    private void populateHandsWithDefuse() {
        for (int i = 0; i < players.size(); i++) {
            String cardId = createCardId(
                    CardType.DEFUSE, GameConstants.NUM_DEFUSES_IN_GAME - i);
            Card defuse = new Card(cardId, CardType.DEFUSE);

            players.get(i).addCardToHand(defuse);
        }
    }

    private void populateHandsWithNonDefuseStartingCards() {
        for (Player player : players) {
            for (int i = 0; i < GameConstants.STARTING_HAND_SIZE - 1; i++) {
                Card card = drawPile.removeTop();
                player.addCardToHand(card);
            }
        }
    }

    public void startGame() {
        if (getIsGameOngoing()) {
            throw new IllegalStateException("error.gameAlreadyStarted");
        }

        addExplodingKittens();
        drawPile.shuffle();

        isGameOngoing = true;
    }

    private void addExplodingKittens() {
        int numKittens = players.size() - 1;
        for (int i = 1; i <= numKittens; i++) {
            String cardId = createCardId(CardType.EXPLODING_KITTEN, i);
            Card kitten = new Card(cardId, CardType.EXPLODING_KITTEN);
            drawPile.addCardToTop(kitten);
        }
    }

    public List<String> getPlayerNames() {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

    public int getCurrentPlayerIndex() {
        return turnManager.getCurrentPlayerIndex();
    }

    public int getStartingPlayerIndex() {
        return GameConstants.STARTING_PLAYER_INDEX;
    }

    public Player getCurrentPlayer() {
        return players.get(getCurrentPlayerIndex());
    }

    public List<String> getCurrentPlayerHandIds() {
        return getCurrentPlayer().getHandIds();
    }

    public boolean canPlaySelected() {
        List<Card> selectedCards = getCurrentPlayer().getSelectedCards();
        if (selectedCards.size() != 1) {
            return false;
        }

        CardType type = selectedCards.get(0).getType();
        return !GameConstants.CONDITIONAL_PLAY_CARDTYPES.contains(type);
    }

    public CardType playSelectedCards() {
        if (!canPlaySelected()) {
            throw new IllegalStateException("error.cannotPlaySelectedCards");
        }

        List<Card> selectedCards = getCurrentPlayer().getSelectedCards();
        CardType cardType = selectedCards.get(0).getType();

        for (Card card : selectedCards) {
            card.toggleSelected();

            getCurrentPlayer().removeCardFromHand(card);
            discardPile.addCardToTop(card);
        }

        applyByCardType(cardType);

        return cardType;
    }

    private void applyByCardType(CardType cardType) {
        switch (cardType) {
            case ATTACK:
                applyAttack();
                break;
            case SHUFFLE:
                applyShuffle();
                break;
            case SKIP:
                applySkip();
                break;
            case CATOMIC_BOMB:
                applyCatomicBomb();
                break;
            case SUPER_SKIP:
                applySuperSkip();
                break;
            case CLONE:
                applyClone();
                break;
            case SWAP_TOP_AND_BOTTOM:
                applySwapTopAndBottom();
                break;
            case DRAW_FROM_THE_BOTTOM:
                applyDrawFromTheBottom();
                break;
            case WINNER_WINNER_CATNIP_DINNER:
                applyWinnerWinnerCatnipDinner();
                break;
            case RAGEBAIT:
                applyRagebait();
                break;
            case RECYCLE:
                applyRecycle();
                break;
            case DOUBLE_UP:
                applyDoubleUp();
                break;
            case MILD_SHUFFLE:
                applyMildShuffle();
                break;
            default:
                break;
        }
    }

    public String getTopDiscardId() {
        return discardPile.peekTop().getId();
    }

    public boolean canDrawFromDiscard() {
        return false;
    }

    public boolean canEndTurn() {
        return turnManager.getDrawCount() == 0;
    }

    public boolean isDrawPileEmpty() {
        return drawPile.isEmpty();
    }

    public boolean getIsGameOngoing() {
        return isGameOngoing;
    }

    public boolean getCanDraw() {
        return isGameOngoing && turnManager.getDrawCount() > 0;
    }

    public boolean getIsFaceUp() {
        return isFaceUp;
    }

    public void changeCurrentPlayerIndex(int newPlayerIndex) {
        turnManager.setCurrentPlayerIndex(newPlayerIndex);
    }

    public void setFaceUpToFalse() {
        isFaceUp = false;
    }

    public Card drawFromPile() {
        Card card = drawPile.peekTop();

        if (card.getType() != CardType.EXPLODING_KITTEN) {
            drawPile.removeTop();
            getCurrentPlayer().addCardToHand(card);
        }

        turnManager.decrementDrawCount();
        getCurrentPlayer().deselectHandCards();

        return card;
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public void toggleFaceUp() {
        isFaceUp = !isFaceUp;
    }

    public void toggleSelectedPlayerCardAt(int handCardIndex) {
        getCurrentPlayer().toggleSelectedHandCardAt(handCardIndex);
    }

    public void advanceTurn() {
        if (!canEndTurn()) {
            throw new IllegalStateException("error.cannotEndTurn");
        }
        getCurrentPlayer().deselectHandCards();

        turnManager.incrementTurn(players);
    }

    private boolean isPlayerAlive(int playerIndex) {
        return players.get(playerIndex).isAlive();
    }

    void setIsGameOngoing(boolean isGameOngoing) {
        this.isGameOngoing = isGameOngoing;
    }

    void setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public boolean isDefusable() {
        return currentPlayerHasCardType(CardType.DEFUSE) ||
                canUseCloneAsDefuse() ||
                currentPlayerHasCardType(CardType.GODCAT);
    }

    private boolean currentPlayerHasCardType(CardType cardType) {
        List<Card> currentPlayerHand = getCurrentPlayer().getHand();

        return currentPlayerHand.stream()
                .anyMatch(card -> card.getType() == cardType);
    }

    private boolean canUseCloneAsDefuse() {
        Card topDiscardCard = discardPile.peekTop();

        return currentPlayerHasCardType(CardType.CLONE) &&
                (topDiscardCard.getType() == CardType.DEFUSE);
    }

    public void playDefuse(int drawPileIndex) {
        Card defuse = findDefuser();
        getCurrentPlayer().removeCardFromHand(defuse);
        discardPile.addCardToTop(defuse);

        Card explodingKitten = drawPile.removeTop();
        drawPile.insertCardAt(explodingKitten, drawPileIndex);
    }

    private Card findDefuser() {
        if (currentPlayerHasCardType(CardType.DEFUSE)) {
            return getCurrentPlayerCardOfType(CardType.DEFUSE);
        }
        else if (canUseCloneAsDefuse()) {
            return getCurrentPlayerCardOfType(CardType.CLONE);
        }
        else if (currentPlayerHasCardType(CardType.GODCAT)) {
            return getCurrentPlayerCardOfType(CardType.GODCAT);
        }

        throw new IllegalStateException("error.currentPlayerNoDefuser");
    }

    private Card getCurrentPlayerCardOfType(CardType cardType) {
        for (Card card : getCurrentPlayer().getHand()) {
            if (card.getType() == cardType) {
                return card;
            }
        }

        throw new IllegalStateException("error.currentPlayerNoCardOfCardtype");
    }

    public void playExplode() {
        drawPile.removeTop();

        getCurrentPlayer().deselectHandCards();
        getCurrentPlayer().eliminate();

        turnManager.incrementTurn(players);
    }

    void applyAttack() {
        // TODO
    }

    void applyShuffle() {
        // TODO
    }

    void applySkip() {
        turnManager.decrementDrawCount();
        if (canEndTurn()) {
            advanceTurn();
        }
    }

    public List<String> getSeeTheFutureCardIds() {
        List<Card> topCards = drawPile.peekTopNCards(
                GameConstants.SEE_THE_FUTURE_PEEK_COUNT);

        return topCards.stream()
                .map(Card::getId)
                .collect(Collectors.toList());
    }

    void applyCatomicBomb() {
        List<Card> explodingKittens = new ArrayList<>();
        List<Card> others = new ArrayList<>();

        while (!drawPile.isEmpty()) {
            Card card = drawPile.removeTop();
            if (card.getType() == CardType.EXPLODING_KITTEN) {
                explodingKittens.add(card);
            }
            else {
                others.add(card);
            }
        }

        for (Card card : others) {
            drawPile.addCardToBottom(card);
        }

        for (Card card : explodingKittens) {
            drawPile.addCardToTop(card);
        }

        turnManager.setDrawCount(0);
        advanceTurn();
    }

    void applySuperSkip() {
        turnManager.setDrawCount(0);
        advanceTurn();
    }

    public void applyGodcat(CardType cardType) {
        boolean isValidGodcatPlay = cardType != CardType.GODCAT &&
                !GameConstants.CONDITIONAL_PLAY_CARDTYPES.contains(cardType);

        if (isValidGodcatPlay) {
            applyByCardType(cardType);
        }
        else {
            throw new IllegalArgumentException("error.cannotPlaySelectedCards");
        }
    }

    void applyClone() {
        // TODO
    }

    void applySwapTopAndBottom() {
        if (drawPile.size() <= 1) {
            return;
        }

        Card top = drawPile.removeTop();
        Card bottom = drawPile.removeBottom();

        drawPile.addCardToTop(bottom);
        drawPile.addCardToBottom(top);
    }

    void applyDrawFromTheBottom() {
        // TODO
    }

    void applyTargetedAttack() {
        // TODO
    }

    void applyWinnerWinnerCatnipDinner() {
        // TODO
    }

    void applyRagebait() {
        // TODO
    }

    void applyRecycle() {
        // TODO
    }

    void applyDoubleUp() {
        // TODO
    }

    void applyMildShuffle() {
        // TODO
    }

}
