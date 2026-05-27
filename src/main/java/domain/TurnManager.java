package domain;

import java.util.ArrayList;
import java.util.List;

public final class TurnManager {
    private final List<Player> players;
    //    private int currentPlayerIndex;

    public TurnManager(List<Player> players) {
        this.players = players;
    }
}
