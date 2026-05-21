package domain;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private final List<Player> players;
    private boolean isGameOngoing;
    private boolean canDraw;
    private boolean isFaceUp;
    private final Deck drawPile;
    private final Deck discardPile;
    private TurnManager turnManager;

    public Game(List<String> playerNames, Deck drawPile, Deck discardPile) {
        if (playerNames == null) {
            throw new IllegalArgumentException("Player list cannot be null.");
        }

        if (playerNames.size() < MIN_PLAYERS || playerNames.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Invalid player count: " + playerNames.size());
        }

        this.players = new ArrayList<>();
        for (String name : playerNames) {
            this.players.add(new Player(name));
        }

        this.isGameOngoing = false;
        this.canDraw = false;
        this.isFaceUp = false;

        this.drawPile = drawPile;
        this.discardPile = discardPile;

        this.turnManager = null;
    }

    public void startGame() {
        this.turnManager = new TurnManager(this.players);

        for (Player p : this.players) {
            p.addCardToHand(new Card(CardType.DEFUSE));
            for (int i = 0; i < 7; i++) {
                p.addCardToHand(this.drawPile.removeTop());
            }
        }

        int defusesToAdd = 6 - this.players.size();
        for (int i = 0; i < defusesToAdd; i++) {
            this.drawPile.addCard(new Card(CardType.DEFUSE));
        }

        int kittensToAdd = this.players.size() - 1;
        for (int i = 0; i < kittensToAdd; i++) {
            this.drawPile.addCard(new Card(CardType.EXPLODING_KITTEN));
        }

        this.drawPile.shuffle();

        this.isGameOngoing = true;
        this.canDraw = true;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public boolean getIsGameOngoing() {
        return this.isGameOngoing;
    }

    public boolean canDraw() {
        return this.canDraw;
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
