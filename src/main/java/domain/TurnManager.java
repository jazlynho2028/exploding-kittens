package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

public class TurnManager {

    private final List<Player> players;
    private int currentPlayerIndex;
    private int roundCount;
    private int drawCount;

    @SuppressFBWarnings(
            value = {"CT_CONSTRUCTOR_THROW"},
            justification = "CT_CONSTRUCTOR_THROW: TurnManager is an internal domain object. " +
                    "Finalizer attack is not a concern. It is TurnManager's responsibility " +
                    "to verify its inputs, and it cannot be made a final class for testability."
    )
    public TurnManager(List<Player> players) {
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
        return GameConstants.STARTING_PLAYER_INDEX;
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

        if (currentPlayerIndex == GameConstants.STARTING_PLAYER_INDEX) {
            incrementRound();
        }

        incrementDrawCount();
    }

}
