package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static domain.DeckBuilder.createCardId;

public class Game {

    private final List<Player> players;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    private boolean isGameOngoing;
    private boolean isFaceUp;
    private boolean canPlay;

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
        canPlay = false;
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

        changeCurrentPlayerIndex(GameConstants.STARTING_PLAYER_INDEX);
        isGameOngoing = true;
        canPlay = true;
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
        if (!canPlay) {
            return false;
        }

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

        return applyByCardType(cardType);
    }

    CardType applyByCardType(CardType cardType) {
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
                return applyClone();
            case SWAP_TOP_AND_BOTTOM:
                applySwapTopAndBottom();
                break;
            case WINNER_WINNER_CATNIP_DINNER:
                applyWinnerWinnerCatnipDinner();
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

        return cardType;
    }

    public String getTopDiscardId() {
        if (discardPile.isEmpty()) {
            return "global.empty";
        }
        return discardPile.peekTop().getId();
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

    void setIsGameOngoing(boolean isGameOngoing) {
        this.isGameOngoing = isGameOngoing;
    }

    public boolean getCanDraw() {
        return isGameOngoing && turnManager.getDrawCount() > 0;
    }

    public boolean getCanPlay() {
        return isGameOngoing && canPlay;
    }

    void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean getIsFaceUp() {
        return isFaceUp;
    }

    void setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public void changeCurrentPlayerIndex(int newPlayerIndex) {
        if (!getAliveIndices().contains(newPlayerIndex)) {
            throw new IllegalStateException("error.playerIsDead");
        }

        isFaceUp = false;
        turnManager.setCurrentPlayerIndex(newPlayerIndex);
    }

    private Card drawCard(Supplier<Card> peek, Runnable remove) {
        Card card = peek.get();

        if (card.getType() != CardType.EXPLODING_KITTEN) {
            remove.run();
            getCurrentPlayer().addCardToHand(card);
        }

        turnManager.decrementDrawCount();
        getCurrentPlayer().deselectHandCards();

        canPlay = false;
        return card;
    }

    public Card drawFromPile() {
        return drawCard(drawPile::peekTop, drawPile::removeTop);
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

    public void endTurn() {
        if (!canEndTurn()) {
            throw new IllegalStateException("error.cannotEndTurn");
        }

        getCurrentPlayer().deselectHandCards();

        if (reachedWinnerWinnerCondition()) {
            eliminateAllButCurrentPlayer();
            isGameOngoing = false;
        }
        else {
            nextTurn();
        }
    }

    private void eliminateAllButCurrentPlayer() {
        for (Player player : players) {
            if (player != getCurrentPlayer()) {
                player.eliminate();
            }
        }
    }

    private void nextTurn() {
        canPlay = true;
        isFaceUp = false;
        turnManager.incrementTurn(getAliveIndices());
        turnManager.incrementDrawCount();
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

        if (hasWinner()) {
            isGameOngoing = false;
        }
        else {
            nextTurn();
        }
    }

    private boolean hasWinner() {
        return getAliveIndices().size() == 1;
    }

    void applyAttack() {
        // TODO
    }

    void applyShuffle() {
        drawPile.shuffle();
    }

    void applySkip() {
        turnManager.decrementDrawCount();
        if (canEndTurn()) {
            endTurn();
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

        applySkip();
    }

    void applySuperSkip() {
        turnManager.setDrawCount(GameConstants.NUM_DRAW_COUNT_AFTER_SUPER_SKIP);
        endTurn();
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

    CardType applyClone() {
        List<Card> topDiscardCards = discardPile.peekTopNCards(
                GameConstants.CLONE_PEEK_COUNT);

        if (topDiscardCards.size() < GameConstants.CLONE_PEEK_COUNT) {
            throw new IllegalStateException("error.noCardToClone");
        }

        Card clonedCard = topDiscardCards.get(GameConstants.CLONE_CARD_INDEX);
        CardType clonedCardType = clonedCard.getType();

        applyByCardType(clonedCardType);

        return clonedCardType;
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

    public Card drawFromTheBottom() {
        return drawCard(drawPile::peekBottom, drawPile::removeBottom);
    }

    public void applyTargetedAttack(int targetPlayerIndex) {
        getCurrentPlayer().deselectHandCards();
        while (turnManager.getCurrentPlayerIndex() != targetPlayerIndex) {
            turnManager.incrementTurn(getAliveIndices());
        }
        addAttackDrawCount();
    }

    void addAttackDrawCount() {
        if (turnManager.getDrawCount() >= 2) {
            turnManager.setDrawCount(
                    turnManager.getDrawCount() + GameConstants.ATTACK_DRAW_COUNT);
        }
        else {
            turnManager.setDrawCount(GameConstants.ATTACK_DRAW_COUNT);
        }

    }

    boolean reachedWinnerWinnerCondition() {
        if (!getCurrentPlayer().isWinnerWinnerActivated()) {
            return false;
        }

        int activatedRound = getCurrentPlayer().getWinnerWinnerActivatedRound();

        return ((turnManager.getRoundCount() - activatedRound) ==
                GameConstants.WINNER_WINNER_REQUIRED_ROUNDS);
    }

    void applyWinnerWinnerCatnipDinner() {
        int currentRound = turnManager.getRoundCount();

        getCurrentPlayer().activateWinnerWinnerFromRound(currentRound);
    }


    public void applyRagebait(int targetPlayerIndex) {
        Player currentPlayer = getCurrentPlayer();
        Player targetPlayer = players.get(targetPlayerIndex);
        currentPlayer.swapHandWith(targetPlayer);
    }

    void applyDoubleUp() {
        // TODO
    }

    void applyMildShuffle() {
        // TODO
    }

    public Set<Integer> getAliveIndices() {
        return players.stream()
                .filter(Player::isAlive)
                .map(players::indexOf)
                .collect(Collectors.toSet());
    }

    public String getWinnerName() {
        List<String> aliveNames = players.stream()
                .filter(Player::isAlive)
                .map(Player::getName)
                .collect(Collectors.toList());

        if (aliveNames.size() == 1) {
            return aliveNames.get(0);
        }

        throw new IllegalStateException("error.noWinner");
    }

    public Card drawRecycle() {
        discardPile.shuffle();
        return drawCard(discardPile::peekBottom, discardPile::removeBottom);
    }

}
