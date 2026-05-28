package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void decrementDrawCount_fromZero_throwsIllegalStateException() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                turnManager::decrementDrawCount
        );

        assertEquals("error.negativeDrawCount", exception.getMessage());
        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_fromPositiveValue_decrementsCount() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);
        turnManager.incrementDrawCount();

        turnManager.decrementDrawCount();

        assertEquals(0, turnManager.getCurrentDrawCount());
        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementRound_initialZero_success() {
        final int initialRoundCounter = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementRound();

        int actual = turnManager.getRoundCounter();

        assertEquals(initialRoundCounter + 1, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementRound_fromNonZero_success() {
        final int initialRoundCounter = 0;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementRound();
        turnManager.incrementRound();

        int actual = turnManager.getRoundCounter();

        assertEquals(initialRoundCounter + 2, actual);

        EasyMock.verify(mockPlayer1);
    }

    @ParameterizedTest
    @CsvSource({
            "2",
            "3",
            "4"
    })
    public void advanceTurn_fromIndexZero_currentPlayerIndexIncrements(int totalPlayers) {
        List<Player> players = new ArrayList<>();
        Player[] mockPlayers = new Player[totalPlayers];
        for (int i = 0; i < totalPlayers; i++) {
            mockPlayers[i] = EasyMock.createMock(Player.class);
            players.add(mockPlayers[i]);
        }
        EasyMock.replay((Object[]) mockPlayers);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();

        assertEquals(1, turnManager.getCurrentPlayerIndex());

        EasyMock.verify((Object[]) mockPlayers);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 0, 1",
            "3, 1, 2",
            "4, 2, 3"
    })
    public void advanceTurn_fromSecondToLastPlayer_currentIndexReachesMaxValidIndex(int totalPlayers, int initialIndex, int expectedIndex) {
        List<Player> players = new ArrayList<>();
        Player[] mockPlayers = new Player[totalPlayers];
        for (int i = 0; i < totalPlayers; i++) {
            mockPlayers[i] = EasyMock.createMock(Player.class);
            players.add(mockPlayers[i]);
        }
        EasyMock.replay((Object[]) mockPlayers);

        TurnManager turnManager = new TurnManager(players);
        turnManager.setCurrentPlayerIndex(initialIndex);

        turnManager.advanceTurn();

        assertEquals(expectedIndex, turnManager.getCurrentPlayerIndex());

        EasyMock.verify((Object[]) mockPlayers);
    }

    @Test
    public void constructor_emptyPlayerList_throwsException() {
        List<Player> emptyPlayersList = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new TurnManager(emptyPlayersList);
                }
        );

        assertEquals("error.emptyPlayerList", exception.getMessage());
    }

}
