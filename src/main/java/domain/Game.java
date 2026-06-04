package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.stream.Collectors;

import static domain.DeckBuilder.createCardId;
import static domain.GameConstants.*;

public class Game {

    private final List<Player> players;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    private boolean isGameOngoing;
    private boolean isFaceUp;

    private static final List<CardType> UNPLAYABLE_TYPES = List.of(
            CardType.DEFUSE,
            CardType.EXPLODING_KITTEN,
            CardType.CAT_CARD_1,
            CardType.CAT_CARD_2,
            CardType.CAT_CARD_3,
            CardType.CAT_CARD_4,
            CardType.FERAL_CAT
    );

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
        if (numPlayers < MIN_PLAYERS) {
            throw new IllegalArgumentException("error.minPlayers");
        }
    }

    private void verifyMaxPlayers(int numPlayers) {
        if (numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("error.maxPlayers");
        }
    }

    private void populatePlayerHands() {
        populateHandsWithDefuse();

        populateHandsWithNonDefuseStartingCards();
    }

    private void populateHandsWithDefuse() {
        for (int i = 0; i < players.size(); i++) {
            String cardId = createCardId(CardType.DEFUSE, NUM_DEFUSES_IN_GAME - i);
            Card defuse = new Card(cardId, CardType.DEFUSE);

            players.get(i).addCardToHand(defuse);
        }
    }

    private void populateHandsWithNonDefuseStartingCards() {
        for (Player player : players) {
            for (int i = 0; i < STARTING_HAND_SIZE - 1; i++) {
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
            drawPile.addCard(kitten);
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
        return STARTING_PLAYER_INDEX;
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
        return !UNPLAYABLE_TYPES.contains(type);
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
            discardPile.addCard(card);
        }

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
            case SEE_THE_FUTURE:
                break;
            case CATOMIC_BOMB:
                applyCatomicBomb();
                break;
            case SUPER_SKIP:
                applySuperSkip();
                break;
            case GODCAT:
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
            case TARGETED_ATTACK:
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
                throw new IllegalStateException("error.cannotPlaySelectedCards");
        }

        return cardType;
    }

    public String getTopDiscardId() {
        return discardPile.peekTop().getId();
    }

    public boolean canDrawFromDiscard() {
        return false;
    }

    public boolean canEndTurn() {
        return isGameOngoing && turnManager.getDrawCount() == 0;
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
        turnManager.incrementTurn();
    }

    public boolean currentPlayerHasDefuse() {
        List<Card> currentPlayerHand = getCurrentPlayer().getHand();

        return currentPlayerHand.stream()
                .anyMatch(card -> card.getType() == CardType.DEFUSE);
    }

    void setIsGameOngoing(boolean isGameOngoing) {
        this.isGameOngoing = isGameOngoing;
    }

    void setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public void playDefuse(int drawPileIndex) {
        Card defuse = getCurrentPlayerDefuse();
        getCurrentPlayer().removeCardFromHand(defuse);
        discardPile.addCard(defuse);

        Card explodingKitten = drawPile.removeTop();
        drawPile.insertCardAt(explodingKitten, drawPileIndex);
    }

    private Card getCurrentPlayerDefuse() {
        for (Card card : getCurrentPlayer().getHand()) {
            if (card.getType() == CardType.DEFUSE) {
                return card;
            }
        }

        throw new IllegalStateException("error.currentPlayerNoDefuse");
    }

    public void playExplode() {
        drawPile.removeTop();

        getCurrentPlayer().deselectHandCards();
        turnManager.incrementTurn();
        // TODO unalive current player
    }

    void applyAttack() {
        // TODO
    }

    void applyShuffle() {
        // TODO
    }

    void applySkip() {
        // TODO
    }

    void applySeeTheFuture() {
        // TODO
    }

    void applyCatomicBomb() {
        // TODO
    }

    void applySuperSkip() {
        // TODO
    }

    void applyGodcat() {
        // TODO
    }

    void applyClone() {
        // TODO
    }

    void applySwapTopAndBottom() {
        // TODO
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
