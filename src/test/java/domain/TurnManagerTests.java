package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerTests {

    @Test
    public void constructor_emptyPlayerList_throwsException() {
        int numPlayers = 0;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TurnManager(numPlayers)
        );

        assertEquals("error.zeroOrNegativePlayers", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2"
    })
    public void constructor_validPlayerCount_setInitialCounts(int numPlayers) {
        TurnManager turnManager = new TurnManager(numPlayers);

        int currentPlayerIndex = turnManager.getCurrentPlayerIndex();
        int drawCount = turnManager.getDrawCount();
        int roundCount = turnManager.getRoundCount();

        assertEquals(0, currentPlayerIndex);
        assertEquals(1, drawCount);
        assertEquals(1, roundCount);
    }

    @Test
    public void decrementDrawCount_fromZero_throwsIllegalStateException() {
        int numPlayers = 1;

        TurnManager turnManager = new TurnManager(numPlayers);
        turnManager.decrementDrawCount();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                turnManager::decrementDrawCount
        );

        assertEquals("error.negativeDrawCount", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource ({
            "1, 0",
            "2, 1"
    })
    public void decrementDrawCount_positiveDrawCount_decrementedByOne(
            int initialDrawCount, int expectedDrawCount
    ) {
        int numPlayers = 1;

        TurnManager turnManager = new TurnManager(numPlayers);
        turnManager.setDrawCount(initialDrawCount);

        turnManager.decrementDrawCount();

        int actualDrawCount = turnManager.getDrawCount();
        assertEquals(expectedDrawCount, actualDrawCount);
    }

    @ParameterizedTest
    @CsvSource({
            "2,  0, 1,  1, 1",
            "3,  0, 1,  1, 1",
            "4,  0, 1,  1, 1",

            "2,  0, 1,  2, 2",
            "3,  1, 2,  2, 2",
            "4,  2, 3,  2, 2",

            "2,  1, 0,  1, 2",
            "3,  2, 0,  1, 2",
            "4,  3, 0,  1, 2"
    })
    public void incrementTurn_nextPlayerIsAlive_updatesPlayerIndexCorrectly(
            int numPlayers,
            int initialIndex, int expectedIndex,
            int initialRoundCount, int expectedRoundCount) {

        List<Player> players = createPlayersWithExpectations(
                numPlayers, List.of()
        );

        TurnManager turnManager = new TurnManager(numPlayers);

        turnManager.setCurrentPlayerIndex(initialIndex);
        turnManager.setRoundCount(initialRoundCount);

        turnManager.incrementTurn(players);

        assertEquals(expectedIndex, turnManager.getCurrentPlayerIndex());
        assertEquals(expectedRoundCount, turnManager.getRoundCount());
    }

    private List<Player> createPlayersWithExpectations(
            int numPlayers, List<Integer> deadIndices) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            Player player = EasyMock.createMock(Player.class);
            EasyMock.expect(player.isAlive()).andStubReturn(
                    !deadIndices.contains(i)
            );
            EasyMock.replay(player);

            players.add(player);
        }

        return players;
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1,  0, 0,  1, 2,  0, 1",
            "2, 0,  1, 1,  1, 2,  0, 1",

            "3, 1,  0, 2,  1, 1,  0, 1",
            "3, 2,  1, 0,  2, 3,  1, 2",
            "3, 0,  2, 1,  1, 2,  1, 2",

            "4, 1,  0, 2,  1, 1,  0, 1"
    })
    public void incrementTurn_nextPlayerIsDead_updatesPlayerIndexCorrectly(
            int numPlayers, int deadIndex,
            int initialIndex, int expectedIndex,
            int initialRoundCount, int expectedRoundCount,
            int initialDrawCount, int expectedDrawCount) {

        List<Player> players = createPlayersWithExpectations(
                numPlayers, List.of(deadIndex)
        );

        TurnManager turnManager = new TurnManager(numPlayers);

        turnManager.setCurrentPlayerIndex(initialIndex);
        turnManager.setRoundCount(initialRoundCount);
        turnManager.setDrawCount(initialDrawCount);

        turnManager.incrementTurn(players);

        int actualIndex = turnManager.getCurrentPlayerIndex();
        int actualRoundCount = turnManager.getRoundCount();
        int actualDrawCount = turnManager.getDrawCount();

        assertEquals(expectedIndex, actualIndex);
        assertEquals(expectedRoundCount, actualRoundCount);
        assertEquals(expectedDrawCount, actualDrawCount);
    }

    @Test
    public void incrementTurn_nextTwoPlayersAreDead_updatesPlayerIndexCorrectly() {
        int numPlayers = GameConstants.MAX_PLAYERS;
        List<Integer> deadIndices = List.of(numPlayers - 1, 0);

        List<Player> players = createPlayersWithExpectations(
                numPlayers, deadIndices
        );

        TurnManager turnManager = new TurnManager(numPlayers);

        turnManager.setCurrentPlayerIndex(2);
        turnManager.setRoundCount(1);
        turnManager.setDrawCount(0);

        turnManager.incrementTurn(players);

        int actualIndex = turnManager.getCurrentPlayerIndex();
        int actualRoundCount = turnManager.getRoundCount();
        int actualDrawCount = turnManager.getDrawCount();

        assertEquals(1, actualIndex);
        assertEquals(2, actualRoundCount);
        assertEquals(1, actualDrawCount);
    }

    @Test
    public void incrementTurn_nextThreePlayersAreDead_updatesPlayerIndexCorrectly() {
        int numPlayers = GameConstants.MAX_PLAYERS;
        List<Integer> deadIndices = List.of(numPlayers - 1, 0, 1);

        List<Player> players = createPlayersWithExpectations(
                numPlayers, deadIndices
        );

        TurnManager turnManager = new TurnManager(numPlayers);

        turnManager.setCurrentPlayerIndex(2);
        turnManager.setRoundCount(1);
        turnManager.setDrawCount(0);

        turnManager.incrementTurn(players);

        int actualIndex = turnManager.getCurrentPlayerIndex();
        int actualRoundCount = turnManager.getRoundCount();
        int actualDrawCount = turnManager.getDrawCount();

        assertEquals(2, actualIndex);
        assertEquals(2, actualRoundCount);
        assertEquals(1, actualDrawCount);
    }

    @Test
    public void incrementTurn_allPlayersAreDead_failed() {
        int numPlayers = 2;
        List<Integer> deadIndices = List.of(0, 1);

        List<Player> players = createPlayersWithExpectations(
                numPlayers, deadIndices
        );

        TurnManager turnManager = new TurnManager(numPlayers);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                turnManager.incrementTurn(players));

        String expectedMsg = "error.noAlivePlayers";
        String actualMsg = exception.getMessage();

        assertEquals(expectedMsg, actualMsg);
    }

    @ParameterizedTest
    @CsvSource({
            "-1",
            "1"
    })
    public void setCurrentPlayerIndex_invalidIndex_failed(int newPlayerIndex) {
        int numPlayers = 1;

        String expectedMsg = "error.invalidPlayerIndex";

        TurnManager turnManager = new TurnManager(numPlayers);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                turnManager.setCurrentPlayerIndex(newPlayerIndex));

        String actualMsg = exception.getMessage();
        assertEquals(expectedMsg, actualMsg);
    }

    @ParameterizedTest
    @CsvSource({
            "0",
            "1"
    })
    public void setCurrentPlayerIndex_validIndex_setNewIndex(int expectedNewIndex) {
        int numPlayers = 2;

        TurnManager turnManager = new TurnManager(numPlayers);

        turnManager.setCurrentPlayerIndex(expectedNewIndex);

        int actualNewIndex = turnManager.getCurrentPlayerIndex();
        assertEquals(expectedNewIndex, actualNewIndex);
    }

    @ParameterizedTest
    @CsvSource ({
            "0, 1",
            "1, 2"
    })
    public void incrementDrawCount_validDrawCount_incrementedByOne(
            int initialDrawCount, int expectedDrawCount
    ) {
        int numPlayers = 1;

        TurnManager turnManager = new TurnManager(numPlayers);
        turnManager.setDrawCount(initialDrawCount);

        turnManager.incrementDrawCount();

        int actualDrawCount = turnManager.getDrawCount();
        assertEquals(expectedDrawCount, actualDrawCount);
    }

}