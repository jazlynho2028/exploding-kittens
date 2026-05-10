package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnManagerTests {

    @Test
    public void testGetCurrentPlayerIndex_BeforeTurnsWithFourPlayers() {
        final int firstPlayerIndex = 0;
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);
        Player mockPlayer4 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);
        players.add(mockPlayer3);
        players.add(mockPlayer4);

        TurnManager turnManager = new TurnManager(players);
        int actual = turnManager.getCurrentPlayerIndex();

        assertEquals(firstPlayerIndex, actual);
    }
}
