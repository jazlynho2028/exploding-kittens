package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import static domain.GameConstants.ONE_CARD;
import static domain.GameConstants.STARTING_PLAYER_INDEX;

public class TurnManager {

    private final int numPlayers;
    private int currentPlayerIndex;
    private int roundCount;
    private int drawCount;

    @SuppressFBWarnings(
            value = {"CT_CONSTRUCTOR_THROW"},
            justification = "CT_CONSTRUCTOR_THROW: TurnManager is an internal domain object. " +
                    "Finalizer attack is not a concern. It is TurnManager's responsibility " +
                    "to verify its inputs, and it cannot be made a final class for testability."
    )
    public TurnManager(int numPlayers) {
        if (numPlayers < 1) {
            throw new IllegalArgumentException("error.zeroOrNegativePlayers");
        }
        this.numPlayers = numPlayers;
        roundCount = 1;
        drawCount = 1;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setCurrentPlayerIndex(int newPlayerIndex) {
        if (newPlayerIndex < 0 || newPlayerIndex >= numPlayers) {
            throw new IllegalArgumentException("error.invalidPlayerIndex");
        }
        currentPlayerIndex = newPlayerIndex;
    }

    public void decrementDrawCount() {
        if (drawCount <= 0) {
            throw new IllegalStateException("error.negativeDrawCount");
        }
        drawCount--;
    }

    public void incrementTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;

        if (currentPlayerIndex == STARTING_PLAYER_INDEX) {
            roundCount++;
        }

        drawCount++;
    }

    public void incrementDrawCount(int drawCount) {
        this.drawCount += ONE_CARD;
    }

    void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }
}