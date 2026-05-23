package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {

    @Test
    public void testPeekTop_EmptyDeck() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards);

        assertThrows(UnsupportedOperationException.class, deck::peekTop);
        assertEquals(0, cards.size());
    }

    @Test
    public void testPeekTop_OneCardDeck() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(1, cards.size());
        assertSame(card1, cards.peekFirst());

        EasyMock.verify(card1);
    }
}