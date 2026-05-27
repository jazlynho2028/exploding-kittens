package domain;

import java.util.ArrayList;
import java.util.List;

public final class TurnManager {
    private final List<Player> players;
    //    private int currentPlayerIndex;
    private int currentDrawCount;
    private int currentRound;

    public TurnManager(List<Player> players) {
        this.players = players;
        this.currentDrawCount = 0;
        this.currentRound = 0;
    }

    public int getCurrentDrawCount() {
        return this.currentDrawCount;
    }

    public void incrementRound() {
        this.currentRound++;
    }

    public void incrementDrawCount() {
        this.currentDrawCount++;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }
}
