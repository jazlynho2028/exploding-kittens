package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static domain.DeckBuilder.createCardId;

public final class Game {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private static final int STARTING_HAND_SIZE = 6;

    private final List<Player> players;
    private final List<String> playerNames;

    private boolean isGameOngoing;
    private boolean isFaceUp;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    public Game(List<String> playerNames, Deck drawPile, Deck discardPile) {
        if (playerNames.size() < MIN_PLAYERS || playerNames.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("error.invalidPlayerCount");
        }

        this.playerNames = List.copyOf(playerNames);
        this.drawPile = new Deck(new ArrayDeque<>(drawPile.getCards()));
        this.discardPile = new Deck(new ArrayDeque<>(discardPile.getCards()));

        this.players = new ArrayList<>();
        for (String name : playerNames) {
            this.players.add(new Player(name));
        }

        this.isGameOngoing = false;
        this.isFaceUp = false;

        this.turnManager = new TurnManager(this.players);

        populatePlayerHands();
    }

    private void populatePlayerHands() {
        final int normalCardCount = 5;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            int defuseId = normalCardCount - i;
            String cardId = createCardId(CardType.DEFUSE, defuseId);

            player.addCardToHand(new Card(cardId, CardType.DEFUSE));
            for (int j = 0; j < STARTING_HAND_SIZE - 1; j++) {
                player.addCardToHand(drawPile.removeTop());
            }
        }
    }

    public void startGame() {

    }

    void addExplodingKittensToDrawPile() {

    }

    List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public int getCurrentPlayerIndex() {
        return -1;
    }

    public int getStartingPlayerIndex() {
        return -1;
    }

    public List<String> getCurrentPlayerHandIds() {
        return null;
    }

    public boolean canPlaySelected() {
        return false;
    }

    private boolean isValidOneCard(List<Card> selectedCards) {
        return false;
    }

    private boolean isCatCard(Card card) {
        return false;
    }

    private boolean isValidTwoCards(List<Card> selectedCards) {
        return false;
    }

    private boolean isValidThreeCards(List<Card> selectedCards) {
        return false;
    }

    private boolean cardIsNotType(Card card, CardType type) {
        return false;
    }

    private List<Card> getSelectedCards() {
        return null;
    }

    public boolean canEndTurn() {
        return false;
    }

    public boolean isDrawPileEmpty() {
        return false;
    }

    public boolean getIsGameOngoing() {
        return false;
    }

    public boolean getCanDraw() {
        return false;
    }

    public boolean getIsFaceUp() {
        return false;
    }

    Deck getDrawPile() {
        return null;
    }

    Deck getDiscardPile() {
        return null;
    }

    TurnManager getTurnManager() {
        return this.turnManager;
    }

    public void changeCurrentPlayerIndex(int newPlayerIndex) {

    }

    public void setFaceUpToFalse() {

    }

    public void drawFromPile() {

    }

    public void setIsFaceUpToOpposite() {

    }

    public void setIsSelectedOfPlayerCardAtIndexToOpposite(int handCardIndex) {

    }
}
