package domain;

import java.util.List;

import static domain.GameConstants.STARTING_PLAYER_INDEX;

public class TurnManager {

    private final List<Player> players;
	private int currentPlayerIndex;
    private int roundCount;
    private int drawCount;

    public TurnManager(final List<Player> players) {
        if (players.isEmpty()) {
            throw new IllegalArgumentException("error.emptyPlayerList");
        }
        this.players = List.copyOf(players);
        currentPlayerIndex = 0;
        drawCount = 0;
        roundCount = 0;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getStartingPlayerIndex() {
        return STARTING_PLAYER_INDEX;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setCurrentPlayerIndex(int newPlayerIndex) {
        currentPlayerIndex = newPlayerIndex;
    }

    public void incrementDrawCount() {
        drawCount++;
    }

    public void decrementDrawCount() {
        if (drawCount <= 0) {
            throw new IllegalStateException("error.negativeDrawCount");
        }
        drawCount--;
    }

    public void incrementRound() {
        roundCount++;
    }

    public void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        if (currentPlayerIndex == STARTING_PLAYER_INDEX) {
            incrementRound();
        }

        incrementDrawCount();
    }

}
