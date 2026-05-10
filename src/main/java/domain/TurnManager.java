package domain;

import java.util.ArrayList;
import java.util.List;

public final class TurnManager {
//    private int startingPlayerIndex;
    private int currentPlayerIndex;
    private List<Player> players;
//    private int roundCounter;
//    private int currentDrawCount;

    public TurnManager(List<Player> players) {
        if (players == null) {
            throw new IllegalArgumentException("Player list cannot be null.");
        }
        this.players = new ArrayList<>(players);
        this.currentPlayerIndex = 0;
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }


    public void endTurn() {

    }

    public void advanceTurn() {

    }

    public void skipTurn() {

    }

    public void incrementCurrentDrawCount() {

    }
}
