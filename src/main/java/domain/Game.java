package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Game {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    private static final int STARTING_HAND_SIZE = 7;
    private static final int BASE_DEFUSE_COUNT = 6;

    private final List<Player> players;
    private boolean isGameOngoing;
    private boolean canDraw;
    private boolean isFaceUp;
    private final Deck drawPile;
    private final Deck discardPile;
    private TurnManager turnManager;

    public Game(List<String> playerNames, Deck drawPile, Deck discardPile, List<String> cardIds) {
        if (playerNames == null) {
            throw new GameException("error.playerListNull");
        }

        if (playerNames.size() < MIN_PLAYERS || playerNames.size() > MAX_PLAYERS) {
            throw new GameException("error.invalidPlayerCount");
        }

        if (cardIds == null || cardIds.isEmpty()) {
            throw new GameException("error.cardIdsNullOrEmpty");
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
        if (this.isGameOngoing) {
            throw new GameException("error.gameAlreadyStarted");
        }

        this.turnManager = new TurnManager(this.players);

        for (Player p : this.players) {
            p.addCardToHand(new Card(CardType.DEFUSE));
            for (int i = 0; i < STARTING_HAND_SIZE; i++) {
                p.addCardToHand(this.drawPile.removeTop());
            }
        }

        int defusesToAdd = BASE_DEFUSE_COUNT - this.players.size();
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
        return Collections.unmodifiableList(this.players);
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
