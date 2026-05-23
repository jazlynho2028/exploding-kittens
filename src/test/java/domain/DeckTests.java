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

    @Test
    public void testPeekTop_MultipleDifferentCards() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, cards.size());
        assertSame(card1, cards.peekFirst());
        assertSame(card2, cards.peekLast());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void testPeekTop_MultipleDuplicateCards() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, cards.size());
        assertSame(card1, cards.peekFirst());
        assertSame(card1, cards.peekLast());

        EasyMock.verify(card1);
    }
}