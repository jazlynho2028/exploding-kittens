package domain;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;

// Temporary Deck class so I can implement DeckBuilder class
public class Deck {
    private final Deque<Card> deck;

    public Deck(Collection<Card> cards) {
        this.deck = new LinkedList<>(cards);
    }

    public int size() {
        return deck.size();
    }

    public Deque<Card> getCards() {
        return deck;
    }
}
