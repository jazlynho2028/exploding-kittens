package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplySkipTests {
    @Test
    public void applySkip_drawCountOne_TurnAdvances() {
        final int expectedReturnValue = 0;
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        List<Player> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

        mockTurnManager.decrementDrawCount();
        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnValue);
        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnValue);
        EasyMock.expect(mockTurnManager.getCurrentPlayerIndex()).andReturn(expectedReturnValue);
        mockPlayer1.deselectHandCards();
        mockTurnManager.incrementTurn();

        EasyMock.replay(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);

        Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
        game.setIsGameOngoing(true);

        game.applySkip();

        EasyMock.verify(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);
    }

    @Test
    public void applySkip_drawCountTwo_TurnNotAdvanced() {
        final int expectedDrawCount = 1;
        Player mockPlayer1 = EasyMock.createMock(Player.class);
        Player mockPlayer2 = EasyMock.createMock(Player.class);
        List<Player> players = new ArrayList<>();
        players.add(mockPlayer1);
        players.add(mockPlayer2);

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

        mockTurnManager.decrementDrawCount();
        EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedDrawCount);

        EasyMock.replay(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);

        Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
        game.setIsGameOngoing(true);

        game.applySkip();

        EasyMock.verify(mockPlayer1, mockPlayer2, mockDrawPile, mockDiscardPile, mockTurnManager);
    }



}
