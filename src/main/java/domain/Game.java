package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static domain.DeckBuilder.createCardId;
import static domain.GameConstants.MIN_PLAYERS;
import static domain.GameConstants.MAX_PLAYERS;
import static domain.GameConstants.STARTING_HAND_SIZE;

public class Game implements GameData {

    private final List<Player> players;
    private final List<String> playerNames;

    private boolean isGameOngoing;
    private boolean isFaceUp;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    public Game(List<Player> players, Deck drawPile, Deck discardPile) {
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("error.invalidPlayerCount");
        }

        this.players = List.copyOf(players);
        this.drawPile = new Deck(new ArrayDeque<>(drawPile.getCards()));
        this.discardPile = new Deck(new ArrayDeque<>(discardPile.getCards()));

        this.playerNames = new ArrayList<>();
        for (Player player : players) {
            this.playerNames.add(player.getName());
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
        if (isGameOngoing) {
            throw new IllegalStateException("error.gameAlreadyStarted");
        }

        addExplodingKittensToDrawPile();

        drawPile.shuffle();

        isGameOngoing = true;
        turnManager.incrementRound();
        turnManager.incrementDrawCount();
    }

    void addExplodingKittensToDrawPile() {
        int kittensToAdd = players.size() - 1;

        for (int i = 1; i <= kittensToAdd; i++) {
            String cardId = createCardId(CardType.EXPLODING_KITTEN, i);
            Card explodingKitten = new Card(cardId, CardType.EXPLODING_KITTEN);
            drawPile.addCard(explodingKitten);
        }
    }

    public List<String> getPlayerNames() {
        return List.copyOf(playerNames);
    }

    List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public int getCurrentPlayerIndex() {
        return turnManager.getCurrentPlayerIndex();
    }

    public int getStartingPlayerIndex() {
        return -1;
    }

    public List<String> getCurrentPlayerHandIds() {
        Player currentPlayer = players.get(getCurrentPlayerIndex());

        List<String> ids = new ArrayList<>();
        for (Card card : currentPlayer.getHand()) {
            ids.add(card.getId());
        }

        return ids;
    }

    public boolean canPlaySelected() {
        List<Card> selectedCards = getSelectedCards();

        return isValidOneCard(selectedCards) ||
                isValidTwoCards(selectedCards) ||
                isValidThreeCards(selectedCards);
    }

     private boolean isValidOneCard(List<Card> selectedCards) {
        if (selectedCards.size() == 1) {
            Card first = selectedCards.get(0);

            return cardIsNotType(first, CardType.EXPLODING_KITTEN) &&
                    cardIsNotType(first, CardType.DEFUSE) &&
                    !isCatCard(first);
        }
        return false;
    }

    private boolean isCatCard(Card card) {
        return card.getType().name().contains("CAT_CARD");
    }

    private boolean isValidTwoCards(List<Card> selectedCards) {
        if (selectedCards.size() == 2) {
            Card first = selectedCards.get(0);
            Card second = selectedCards.get(1);

            return first.getType() == second.getType();
        }
        return false;
    }

    private boolean isValidThreeCards(List<Card> selectedCards) {
        final int targetNum = 3;

        if (selectedCards.size() == targetNum) {
            Card first = selectedCards.get(0);
            Card second = selectedCards.get(1);
            Card third = selectedCards.get(2);

            return first.getType() == second.getType() &&
                    second.getType() == third.getType();
        }
        return false;
    }

    private boolean cardIsNotType(Card card, CardType type) {
        return card.getType() != type;
    }

    private List<Card> getSelectedCards() {
        List<Card> currentPlayerHand = players.get(getCurrentPlayerIndex()).getHand();
        List<Card> selectedCards = new ArrayList<>();

        for (Card card : currentPlayerHand) {
            if (card.getIsSelected()) {
                selectedCards.add(card);
            }
        }

        return selectedCards;
    }

    public boolean canEndTurn() {
        return turnManager.getDrawCount() == 0;
    }

    public boolean isDrawPileEmpty() {
        return false;
    }

    public boolean getIsGameOngoing() {
        return this.isGameOngoing;
    }

    public boolean getCanDraw() {
        return false;
    }

    public boolean getIsFaceUp() {
        return false;
    }

    Deck getDrawPile() {
        return drawPile;
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

    public void drawFromPile(int playerIndex) {

    }

    public void setIsFaceUpToOpposite() {

    }

    public void setIsSelectedOfPlayerCardAtIndexToOpposite(int handCardIndex) {

    }
}
