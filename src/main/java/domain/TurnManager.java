package domain;

import java.util.List;

public class TurnManager {
    private int startingPlayerIndex;
    private int currentPlayerIndex;
    private List<Player> players;
    private int roundCounter;
    private int currentDrawCount;

    public TurnManager(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return -1;
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
