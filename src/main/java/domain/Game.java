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

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    public boolean getIsGameOngoing() {
        return this.isGameOngoing;
    }

    public boolean getIsFaceUp() {
        return this.isFaceUp;
    }

    public Deck getDrawPile() {
        return this.drawPile;
    }

    public Deck getDiscardPile() {
        return this.discardPile;
    }

    public TurnManager getTurnManager() {
        return this.turnManager;
    }
}
