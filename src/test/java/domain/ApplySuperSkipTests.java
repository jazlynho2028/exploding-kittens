package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ApplySuperSkipTests {

    @Test
    public void applySuperSkip_drawCountOne_TurnAdvances() {
        final int firstExpectedDrawCount = 1;
        final int ExpectedIntZero = 0;
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        List<Player> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(firstExpectedDrawCount);
        mockTurnManager.decrementDrawCount();
        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(ExpectedIntZero);
        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(ExpectedIntZero);
        EasyMock.expect(mockTurnManager.getCurrentPlayerIndex()).andReturn(ExpectedIntZero);
        mockPlayer1.deselectHandCards();
        mockTurnManager.incrementTurn();

        EasyMock.replay(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);

        Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
        game.setIsGameOngoing(true);

        game.applySuperSkip();

        EasyMock.verify(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);
    }
}
