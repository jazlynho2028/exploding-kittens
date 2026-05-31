package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.stream.Collectors;

import static domain.DeckBuilder.createCardId;
import static domain.GameConstants.*;

public class Game {

    private List<Player> players;
    private Deck drawPile;
    private Deck discardPile;

    private boolean isGameOngoing;
    private boolean isFaceUp;

    private int roundCount;
    private int drawCount;

    private static final List<CardType> UNPLAYABLE_TYPES = List.of(
            CardType.DEFUSE,
            CardType.EXPLODING_KITTEN,
            CardType.CAT_CARD_1,
            CardType.CAT_CARD_2,
            CardType.CAT_CARD_3,
            CardType.CAT_CARD_4,
            CardType.FERAL_CAT
    );

    private TurnManager turnManager;

    @SuppressFBWarnings(
            value = {"EI_EXPOSE_REP2", "CT_CONSTRUCTOR_THROW"},
            justification = "EI_EXPOSE_REP2: TurnManager is injected by for testability. " +
                    "Defensive copy is not desired in this context. " +
                    "CT_CONSTRUCTOR_THROW: Game is an internal domain object. Finalizer " +
                    "attack is not a concern. It is Game's responsibility to " +
                    "verify its inputs, and it cannot be made a final class for testability."
    )
    public Game(List<Player> players, Deck drawPile,
                Deck discardPile, TurnManager turnManager) {

        int numPlayers = players.size();
        verifyMinPlayers(numPlayers);
        verifyMaxPlayers(numPlayers);

        this.players = List.copyOf(players);
        this.drawPile = drawPile;
        this.discardPile = discardPile;
        this.turnManager = turnManager;

        isGameOngoing = false;
        isFaceUp = false;

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

    void populatePlayerHands() {
        populateHandsWithDefuse();

        populateHandsWithNonDefuseStartingCards();
    }

    private void populateHandsWithDefuse() {
        for (int i = 0; i < players.size(); i++) {
            String cardId = createCardId(CardType.DEFUSE, NUM_DEFUSES - i);
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
        roundCount = 1;
        drawCount = 1;
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

    public List<String> getCurrentPlayerHandIds() {
        return getCurrentPlayer().getHandIds();
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public boolean canPlaySelected() {
        List<Card> selectedCards = getCurrentPlayer().getSelectedCards();
        if (selectedCards.size() != 1) {
            return false;
        }
        CardType type = selectedCards.get(0).getType();
        return !UNPLAYABLE_TYPES.contains(type);
    }

    public void playSelectedCards() {
        if (!canPlaySelected()) {
            throw new IllegalStateException("error.cannotPlaySelectedCards");
        }

        List<Card> selectedCards = getCurrentPlayer().getSelectedCards();
        CardType cardType = selectedCards.get(0).getType();

        for (Card card : getCurrentPlayer().getSelectedCards()) {
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
                applySeeTheFuture();
                break;
            case CATOMIC_BOMB:
                applyCatomicBomb();
                break;
            case SUPER_SKIP:
                applySuperSkip();
                break;
            case GODCAT:
                applyGodcat();
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
                applyTargetedAttack();
                break;
            case WINNER_WINNER_CATNIP_DINNER:
                applyWinnerWinnerCatnipDinner();
                break;
            default:
                break;
        }
    }

    void applyAttack() { }

    void applyShuffle() { }

    void applySkip() { }

    void applySeeTheFuture() { }

    void applyCatomicBomb() { }

    void applySuperSkip() { }

    void applyGodcat() { }

    void applyClone() { }

    void applySwapTopAndBottom() { }

    void applyDrawFromTheBottom() { }

    void applyTargetedAttack() { }

    void applyWinnerWinnerCatnipDinner() { }

    public String getTopDiscardId() {
        return "";
    }

    public boolean canDrawFromDiscard() {
        return false;
    }

    public boolean canEndTurn() {
        return getIsGameOngoing() && getDrawCount() == 0;
    }

    public boolean isDrawPileEmpty() {
        return drawPile.isEmpty();
    }

    public boolean getIsGameOngoing() {
        return isGameOngoing;
    }

    public boolean getCanDraw() {
        return getIsGameOngoing() && getDrawCount() > 0;
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

    public void drawFromPile() {
        Card card = drawPile.removeTop();
        Player currentPlayer = getCurrentPlayer();

        turnManager.decrementDrawCount();
        currentPlayer.deselectHandCards();

        currentPlayer.addCardToHand(card);
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
        turnManager.advanceTurn();
        getCurrentPlayer().deselectHandCards();
    }

    int getRoundCount() {
        return roundCount;
    }

    int getDrawCount() {
        return drawCount;
    }

    void setIsFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }
}
