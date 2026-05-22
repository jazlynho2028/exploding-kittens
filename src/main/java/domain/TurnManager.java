package domain;

import java.util.Collections;
import java.util.List;

public final class TurnManager {
    //    private int startingPlayerIndex;
    private int currentPlayerIndex;
    private final List<Player> players;
    //    private int roundCounter;
    //    private int currentDrawCount;

    public TurnManager(final List<Player> players) {
        if (players == null) {
            throw new GameException("error.playerListNull");
        }
        this.players = Collections.unmodifiableList(players);;
        this.currentPlayerIndex = 0;
    }

    public List<Player> getPlayers() {
        return this.players;
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
