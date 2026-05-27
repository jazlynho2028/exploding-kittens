package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void constructor_minimumPlayers_initializesGameCorrectly() {
        final int totalHandSize = 6;
        final int totalPlayers = 2;
        final int normalCards = 5;

        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();

        int cardsToDraw = normalCards * names.size();
        for (int i = 0; i < cardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);
        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        assertEquals(totalHandSize, player1.getHand().size());
        assertEquals(CardType.DEFUSE, player1.getHand().get(0).getType());
        assertEquals(totalHandSize, player2.getHand().size());
        assertEquals(CardType.DEFUSE, player2.getHand().get(0).getType());

        assertFalse(game.getIsGameOngoing());
        assertFalse(game.getIsFaceUp());
        assertNotNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
