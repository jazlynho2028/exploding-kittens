package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {
    @Test
    public void shuffle_emptyDeck_deckStaysEmpty() {
        Deque<Card> cards = new ArrayDeque<>();
        Random mockRandom = EasyMock.createMock(Random.class);
        EasyMock.replay(mockRandom);

        Deck deck = new Deck(cards, mockRandom);

        deck.shuffle();

        assertEquals(0, deck.size());

        EasyMock.verify(mockRandom);
    }

    @Test
    public void peekTop_emptyDeck_throwsUnsupportedOperationException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards);

        assertThrows(UnsupportedOperationException.class, deck::peekTop);
        assertEquals(0, deck.size());
    }

    @Test
    public void peekTop_oneCardDeck_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void peekTop_multipleDifferentCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTop_multipleDuplicateCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void removeTop_emptyDeck_throwsUnsupportedOperationException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards);

        assertThrows(UnsupportedOperationException.class, deck::removeTop);
        assertEquals(0, deck.size());
    }

    @Test
    public void removeTop_oneCardDeck_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(0, deck.size());

        EasyMock.verify(card1);
    }

    @Test
    public void removeTop_multipleDifferentCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards);

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card2, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void removeTop_multipleDuplicateCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards);

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }
}