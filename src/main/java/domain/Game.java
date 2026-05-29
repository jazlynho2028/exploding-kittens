package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static domain.DeckBuilder.createCardId;
import static domain.GameConstants.*;

public class Game {

    private final List<Player> players;

    private boolean isGameOngoing;
    private boolean isFaceUp;
    private final Deck drawPile;
    private final Deck discardPile;
    private final TurnManager turnManager;

    @SuppressFBWarnings(
            value = {"EI_EXPOSE_REP2", "CT_CONSTRUCTOR_THROW"},
            justification = "EI_EXPOSE_REP2: TurnManager is injected by for testability. " +
                    "Defensive copy is not desired in this context. " +
                    "CT_CONSTRUCTOR_THROW: Game is an internal domain object. Finalizer " +
                    "attack is not a concern."
    )
    public Game(List<Player> players, Deck drawPile,
                Deck discardPile, TurnManager turnManager) {

        validatePlayers(players);

        this.players = List.copyOf(players);
        this.drawPile = new Deck(new ArrayDeque<>(drawPile.getCards()));
        this.discardPile = new Deck(new ArrayDeque<>(discardPile.getCards()));
        this.turnManager = turnManager;

        this.isGameOngoing = false;
        this.isFaceUp = false;

        populatePlayerHands();
    }

    private void validatePlayers(List<Player> players) {
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("error.invalidPlayerCount");
        }
    }

    private void populatePlayerHands() {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            int defuseId = NUM_DEFUSES - i;
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

    private void addExplodingKittensToDrawPile() {
        int kittensToAdd = players.size() - 1;

        for (int i = 1; i <= kittensToAdd; i++) {
            String cardId = createCardId(CardType.EXPLODING_KITTEN, i);
            Card explodingKitten = new Card(cardId, CardType.EXPLODING_KITTEN);
            drawPile.addCard(explodingKitten);
        }
    }

    public List<String> getPlayerNames() {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.toList());
    }

    private List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public int getCurrentPlayerIndex() {
        return turnManager.getCurrentPlayerIndex();
    }

    public int getStartingPlayerIndex() {
        return STARTING_PLAYER_INDEX;
    }

    public List<String> getCurrentPlayerHandIds() {
        List<String> ids = new ArrayList<>();

        for (Card card : getCurrentPlayer().getHand()) {
            ids.add(card.getId());
        }

        return ids;
    }

    private Player getCurrentPlayer() {
        return players.get(getCurrentPlayerIndex());
    }

    public boolean canPlaySelected() {
        List<Card> selectedCards = getSelectedCards();

        return isValidOneCard(selectedCards) ||
                isValidNCards(2, selectedCards) ||
                isValidNCards(3, selectedCards);
    }

     private boolean isValidOneCard(List<Card> selectedCards) {
        if (selectedCards.size() == 1) {
            Card first = selectedCards.get(0);

            return !cardIsType(first, CardType.EXPLODING_KITTEN) &&
                    !cardIsType(first, CardType.DEFUSE) &&
                    !isCatCard(first);
        }
        return false;
    }

    private boolean isCatCard(Card card) {
        return card.getType().name().contains("CAT_CARD") ||
                card.getType() == CardType.FERAL_CAT;
    }

    private boolean isValidNCards(int numCards, List<Card> selectedCards) {
        if (selectedCards.size() == numCards) {
            return isAllSameType(selectedCards) && isAllPlayableType(selectedCards);
        }
        return false;
    }

    private boolean isAllSameType(List<Card> selectedCards) {
        if (selectedCards.isEmpty()) {
            return true;
        }

        CardType type = selectedCards.get(0).getType();
        return selectedCards.stream()
                .allMatch(card -> card.getType() == type);
    }

    private boolean isAllPlayableType(List<Card> selectedCards) {
        return selectedCards.stream()
                .noneMatch(card ->
                        cardIsType(card, CardType.EXPLODING_KITTEN) ||
                                cardIsType(card, CardType.DEFUSE)
                );
    }

    private boolean cardIsType(Card card, CardType type) {
        return card.getType() == type;
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

    private Deck getDrawPile() {
        return drawPile;
    }

    private Deck getDiscardPile() {
        return null;
    }

    private TurnManager getTurnManager() {
        return this.turnManager;
    }

    public void changeCurrentPlayerIndex(int newPlayerIndex) {

    }

    public void setFaceUpToFalse() {

    }

    public void drawFromPile() {

    }

    public void toggleFaceUp() {

    }

    public void toggleSelectedPlayerCardAt(int handCardIndex) {

    }

    public void advanceTurn() {
        // if !canEndTurn => throw InvalidStateException "error.cannotEndTurn"
        // turnManager.advanceTurn();
        // getCurrentPlayer().deselectHandCards();
    }
}
