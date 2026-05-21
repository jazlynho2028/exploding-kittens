package domain;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex;

    public TurnManager(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Player collection cannot be null or empty.");
        }
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }

    public void advanceTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
    }
}
