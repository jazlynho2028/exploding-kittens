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

        for (int i = 0; i < totalPlayers; i++) {
            Player player = game.getPlayers().get(i);

            assertEquals(names.get(i), player.getName());
            assertEquals(totalHandSize, player.getHand().size());
            assertEquals(CardType.DEFUSE, player.getHand().get(0).getType());
        }

        assertFalse(game.getIsGameOngoing());
        assertFalse(game.getIsFaceUp());
        assertNotNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_maximumPlayers_initializesGameCorrectly() {
        final int totalHandSize = 6;
        final int totalPlayers = 4;
        final int normalCards = 5;

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();

        int totalCardsToDraw = normalCards * names.size();
        for (int i = 0; i < totalCardsToDraw; i++) {
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

        for (int i = 0; i < totalPlayers; i++) {
            Player player = game.getPlayers().get(i);

            assertEquals(names.get(i), player.getName());
            assertEquals(totalHandSize, player.getHand().size());
            assertEquals(CardType.DEFUSE, player.getHand().get(0).getType());
        }

        assertFalse(game.getIsGameOngoing());
        assertFalse(game.getIsFaceUp());
        assertNotNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooLittlePlayers_throwsIllegalArgumentException() {
        List<String> names = Arrays.asList("Alice");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });
        assertEquals("error.invalidPlayerCount", exception.getMessage());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooManyPlayers_throwsIllegalArgumentException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });
        assertEquals("error.invalidPlayerCount", exception.getMessage());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
