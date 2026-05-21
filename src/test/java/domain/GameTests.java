package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void testConstructor_MinValidPlayers() {
        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(2, game.getPlayers().size());
        assertFalse(game.getIsGameOngoing());
        assertFalse(game.canDraw());
        assertFalse(game.getIsFaceUp());

        assertSame(mockDrawPile, game.getDrawPile());
        assertSame(mockDiscardPile, game.getDiscardPile());

        assertNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
