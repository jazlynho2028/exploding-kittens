package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TurnManagerTests {

    @Test
    public void constructor_emptyPlayerList_throwsException() {
        List<Player> emptyPlayersList = List.of();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new TurnManager(emptyPlayersList);
                }
        );

        assertEquals("error.emptyPlayerList", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2"
    })
    public void constructor_validPlayerCount_zeroInitialCounts(int numPlayers) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            Player player = EasyMock.createMock(Player.class);
            players.add(player);
        }

        TurnManager turnManager = new TurnManager(players);

        int currentPlayerIndex = turnManager.getCurrentPlayerIndex();
        int drawCount = turnManager.getDrawCount();
        int roundCount = turnManager.getRoundCount();

        assertEquals(0, currentPlayerIndex);
        assertEquals(1, drawCount);
        assertEquals(1, roundCount);
    }

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
    public void incrementDrawCount_fromZero_success() {
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.decrementDrawCount();
        turnManager.incrementDrawCount();

        int expected = 1;
        int actual = turnManager.getDrawCount();

        assertEquals(expected, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementDrawCount_fromOne_success() {
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementDrawCount();

        int expected = 2;
        int actual = turnManager.getDrawCount();

        assertEquals(expected, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_fromZero_throwsIllegalStateException() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);
        turnManager.decrementDrawCount();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                turnManager::decrementDrawCount
        );

        assertEquals("error.negativeDrawCount", exception.getMessage());
        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_fromOne_toZero() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);
        turnManager.incrementDrawCount();

        turnManager.decrementDrawCount();

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void decrementDrawCount_fromTwo_toOne() {
        final int expectedDrawCount = 1;
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementDrawCount();
        turnManager.decrementDrawCount();

        assertEquals(expectedDrawCount, turnManager.getDrawCount());

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementRound_initialOne_toTwo() {
        final int initialRoundCounter = 1;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);
        turnManager.incrementRound();

        int actual = turnManager.getRoundCount();

        assertEquals(initialRoundCounter + 1, actual);

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void incrementRound_fromTwo_toThree() {
        final int initialRoundCounter = 1;
        List<Player> players = new ArrayList<>();

        Player mockPlayer1 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.incrementRound();
        turnManager.incrementRound();

        int actual = turnManager.getRoundCount();

        assertEquals(initialRoundCounter + 2, actual);

        EasyMock.verify(mockPlayer1);
    }

    @ParameterizedTest
    @CsvSource({
            "2, 0, 1",
            "3, 0, 1",
            "4, 0, 1",

            "2, 0, 1",
            "3, 1, 2",
            "4, 2, 3",

            "2, 1, 0",
            "3, 2, 0",
            "4, 3, 0"
    })
    public void advanceTurn_boundaryScenarios_updatesPlayerIndexCorrectly(
            int totalPlayers, int initialIndex, int expectedIndex) {
        List<Player> players = new ArrayList<>();
        Player[] mockPlayers = new Player[totalPlayers];
        for (int i = 0; i < totalPlayers; i++) {
            mockPlayers[i] = EasyMock.createMock(Player.class);
            players.add(mockPlayers[i]);
        }

        mockPlayers[initialIndex].deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay((Object[]) mockPlayers);

        TurnManager turnManager = new TurnManager(players);
        turnManager.setCurrentPlayerIndex(initialIndex);

        turnManager.advanceTurn();

        assertEquals(expectedIndex, turnManager.getCurrentPlayerIndex());

        EasyMock.verify((Object[]) mockPlayers);
    }

    @Test
    public void advanceTurn_atLastPlayer_wrapsToZeroExactly() {
        final int startingIndex = 0;
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        Player mockPlayer3 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);
        players.add(mockPlayer3);

        mockPlayer1.deselectHandCards();
        EasyMock.expectLastCall();
        mockPlayer2.deselectHandCards();
        EasyMock.expectLastCall();
        mockPlayer3.deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer1, mockPlayer2, mockPlayer3);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();
        turnManager.advanceTurn();
        turnManager.advanceTurn();

        assertEquals(startingIndex, turnManager.getCurrentPlayerIndex());

        EasyMock.verify(mockPlayer1, mockPlayer2, mockPlayer3);
    }

    @Test
    public void advanceTurn_nextPlayer_sameRoundCount() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);

        mockPlayer1.deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer1, mockPlayer2);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();

        assertEquals(1, turnManager.getRoundCount());

        EasyMock.verify(mockPlayer1, mockPlayer2);
    }

    @Test
    public void advanceTurn_wrapsToStartingPlayer_incrementsRoundCount() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);

        mockPlayer1.deselectHandCards();
        EasyMock.expectLastCall();
        mockPlayer2.deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer1, mockPlayer2);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();
        turnManager.advanceTurn();

        assertEquals(2, turnManager.getRoundCount());

        EasyMock.verify(mockPlayer1, mockPlayer2);
    }

    @Test
    public void advanceTurn_fromDrawCountOne_toTwo () {
        final int expectedDrawCount = 2;
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        players.add(mockPlayer1);

        mockPlayer1.deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer1);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();

        assertEquals(expectedDrawCount, turnManager.getDrawCount());

        EasyMock.verify(mockPlayer1);
    }

    @Test
    public void getCurrentPlayer_oneTurnAdvanced_returnsCorrectPlayerInstance() {
        List<Player> players = new ArrayList<>();
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);

        players.add(mockPlayer1);
        players.add(mockPlayer2);

        mockPlayer1.deselectHandCards();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer1, mockPlayer2);

        TurnManager turnManager = new TurnManager(players);

        turnManager.advanceTurn();
        assertSame(mockPlayer2, turnManager.getCurrentPlayer());

        EasyMock.verify(mockPlayer1, mockPlayer2);
    }

    @Test
    public void getCurrentPlayerHandIds_onePlayer_callsPlayerMethod() {
        int currentPlayerIndex = 0;
        Player mockPlayer = EasyMock.createMock(Player.class);
        List<Player> players = List.of(mockPlayer);
        List<String> expectedHandIds = List.of();

        EasyMock.expect(players.get(currentPlayerIndex).getHandIds()).andReturn(expectedHandIds);

        EasyMock.replay(mockPlayer);

        TurnManager turnManager = new TurnManager(players);

        turnManager.getCurrentPlayerHandIds();

        EasyMock.verify(mockPlayer);
    }

    @Test
    public void getCurrentSelectedCards_onePlayer_callsPlayerMethod() {
        int currentPlayerIndex = 0;
        Player mockPlayer = EasyMock.createMock(Player.class);
        List<Player> players = List.of(mockPlayer);
        List<Card> expectedSelectedCards = List.of();

        EasyMock.expect(players.get(currentPlayerIndex).getSelectedCards())
                .andReturn(expectedSelectedCards);

        EasyMock.replay(mockPlayer);

        TurnManager turnManager = new TurnManager(players);

        turnManager.getCurrentSelectedCards();

        EasyMock.verify(mockPlayer);
    }

    @Test
    public void getStartingPlayerIndex_successfullyReturnsStartingIndex() {
        final int expectedIndex = 0;
        List<Player> players = new ArrayList<>();
        Player mockPlayer = EasyMock.createMock(Player.class);
        players.add(mockPlayer);

        EasyMock.replay(mockPlayer);

        TurnManager turnManager = new TurnManager(players);

        int startingIndex = turnManager.getStartingPlayerIndex();

        assertEquals(expectedIndex, startingIndex);

        EasyMock.verify(mockPlayer);
    }

    @Test
    public void toggleSelectedPlayerCardAt_onePlayer_callsPlayerMethod() {
        int currentPlayerIndex = 0;
        int handCardIndex = 0;
        Player mockPlayer = EasyMock.createMock(Player.class);
        List<Player> players = List.of(mockPlayer);

        players.get(currentPlayerIndex).toggleSelectedHandCardAt(handCardIndex);
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer);

        TurnManager turnManager = new TurnManager(players);

        turnManager.toggleSelectedPlayerCardAt(handCardIndex);

        EasyMock.verify(mockPlayer);
    }

    @Test
    public void updateAfterDraw_onePlayer_decrementDrawAndCallPlayerMethods() {
        int currentPlayerIndex = 0;
        Card drawnCard = EasyMock.createMock(Card.class);
        Player mockPlayer = EasyMock.createMock(Player.class);
        List<Player> players = List.of(mockPlayer);

        players.get(currentPlayerIndex).deselectHandCards();
        EasyMock.expectLastCall();

        players.get(currentPlayerIndex).addCardToHand(drawnCard);

        TurnManager turnManager = EasyMock.createMockBuilder(TurnManager.class)
                .withConstructor(players)
                .addMockedMethod("decrementDrawCount")
                .createMock();

        turnManager.decrementDrawCount();
        EasyMock.expectLastCall();

        EasyMock.replay(mockPlayer, turnManager);

        turnManager.updateAfterDraw(drawnCard);

        EasyMock.verify(mockPlayer, turnManager);
    }

}