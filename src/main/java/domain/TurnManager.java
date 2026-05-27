package domain;

import java.util.ArrayList;
import java.util.List;

public final class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex;

    public TurnManager(List<Player> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Player collection cannot be null or empty.");
        }
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }
}
