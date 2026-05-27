package domain;

import java.util.ArrayList;
import java.util.List;

public final class TurnManager {

    private static final int STARTING_PLAYER_INDEX = 0;

    private final List<Player> players;
    private int currentPlayerIndex;
    private int currentDrawCount;
    private int roundCounter;

    public TurnManager(List<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Players list cannot be empty.");
        }
        this.players = List.copyOf(players);
        currentPlayerIndex = 0;
        currentDrawCount = 0;
        roundCounter = 0;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getCurrentDrawCount() {
        return currentDrawCount;
    }

    //    public int getStartingPlayerIndex() {
    //        return STARTING_PLAYER_INDEX;
    //    }

    //    public Player getCurrentPlayer() {
    //        return players.get(currentPlayerIndex);
    //    }

    public void setCurrentPlayerIndex(int newPlayerIndex) {
        currentPlayerIndex = newPlayerIndex;
    }

    public void incrementDrawCount() {
        currentDrawCount++;
    }

    public void decrementDrawCount() {
        currentDrawCount--;
    }

    public void incrementRound() {
        roundCounter++;
    }

    //    public void advanceTurn() {
    //        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();
    //
    //        if (currentPlayerIndex == STARTING_PLAYER_INDEX) {
    //            incrementRound();
    //        }
    //
    //        incrementDrawCount();
    //    }

    public int getRoundCounter() {
        return this.roundCounter;
    }

}
