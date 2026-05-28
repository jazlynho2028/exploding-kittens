package domain;

import java.util.Collections;
import java.util.List;

public final class TurnManager {
    private static final int STARTING_PLAYER_INDEX = 0;

    private int currentPlayerIndex;
    private final List<Player> players;
    private int roundCounter;
    private int currentDrawCount;

    public TurnManager(final List<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Player list cannot be empty");
        }
        this.players = List.copyOf(players);
        currentPlayerIndex = 0;
        currentDrawCount = 0;
        roundCounter = 0;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getCurrentDrawCount() {
        return currentDrawCount;
    }

    public int getStartingPlayerIndex() {
        return STARTING_PLAYER_INDEX;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public void setCurrentPlayerIndex(int newPlayerIndex) {
        currentPlayerIndex = newPlayerIndex;
    }

    public void incrementDrawCount() {
        this.currentDrawCount++;
    }

    public void decrementDrawCount() {
        if (this.currentDrawCount <= 0) {
            throw new IllegalStateException("error.negativeDrawCount");
        }
        this.currentDrawCount--;
    }

    public void incrementRound() {
        this.roundCounter++;
    }

    public void advanceTurn() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.players.size();

        if (currentPlayerIndex == STARTING_PLAYER_INDEX) {
            incrementRound();
        }

        incrementDrawCount();
    }

}
