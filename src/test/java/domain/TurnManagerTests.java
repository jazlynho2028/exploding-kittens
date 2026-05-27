package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnManagerTests {

    @Test
    public void getCurrentPlayerIndex_minimumPlayers_returnsZero() {
        final int firstPlayerIndex = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);

        EasyMock.replay(mockPlayer1, mockPlayer2);

        TurnManager turnManager = new TurnManager(players);

        int actual = turnManager.getCurrentPlayerIndex();

        assertEquals(firstPlayerIndex, actual);

        EasyMock.verify(mockPlayer1, mockPlayer2);
    }

    @Test
    public void getCurrentPlayerIndex_moreThanOnePlayer_returnsZero() {
        final int firstPlayerIndex = 0;
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);
        players.add(mockPlayer3);

        EasyMock.replay(mockPlayer1, mockPlayer2, mockPlayer3);

        TurnManager turnManager = new TurnManager(players);
        int actual = turnManager.getCurrentPlayerIndex();

        assertEquals(firstPlayerIndex, actual);

        EasyMock.verify(mockPlayer1, mockPlayer2, mockPlayer3);
    }

    @Test
    public void getCurrentPlayerIndex_maximumPlayers_returnsZero() {
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

        EasyMock.replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4);

        TurnManager turnManager = new TurnManager(players);
        int actual = turnManager.getCurrentPlayerIndex();

        assertEquals(firstPlayerIndex, actual);

        EasyMock.verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4);
    }

    @Test
    public void incrementDrawCount_initialZero_success() {
        final int initialDrawCount = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementDrawCount();

        int actual = turnManager.getCurrentDrawCount();

        assertEquals(initialDrawCount + 1, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementDrawCount_fromOne_success() {
        final int initialDrawCount = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementDrawCount();
        turnManager.incrementDrawCount();

        int actual = turnManager.getCurrentDrawCount();

        assertEquals(initialDrawCount + 2, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_initialZero_success() {
        final int initialDrawCount = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.decrementDrawCount();

        int actual = turnManager.getCurrentDrawCount();

        assertEquals(initialDrawCount - 1, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_fromNonZero_success() {
        final int initialDrawCount = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.decrementDrawCount();
        turnManager.decrementDrawCount();

        int actual = turnManager.getCurrentDrawCount();

        assertEquals(initialDrawCount - 2, actual);

        EasyMock.verify(mockPlayer1);
    }
}
