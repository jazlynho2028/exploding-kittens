package domain;

import java.util.Deque;

public class Deck {

    private final Deque<Card> deck;

    public Deck(Deque<Card> deck) {
        this.deck = deck;
    }

    public Card peekTop() {
        if (this.deck.isEmpty()) {
            throw new UnsupportedOperationException("Cannot peek top of empty deck.");
        }

        return this.deck.peekFirst();
    }
}