package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckTests {

    @Test
    public void testPeekTop_EmptyDeck() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards);

        assertThrows(UnsupportedOperationException.class, deck::peekTop);
        assertEquals(0, cards.size());
    }
}