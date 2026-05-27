package domain;

import java.util.*;

public class Deck {

    private final Queue<Card> cards;

    public Deck(Collection<Card> cards) {
        if (cards == null) {
            this.cards = new ArrayDeque<>();
        }
        else {
            this.cards = new ArrayDeque<>(cards);
        }
    }

    public Collection<Card> getCards() {
        return this.cards;
    }

    public Card removeTop() {
        if (this.cards.isEmpty()) {
            throw new IllegalArgumentException("error.emptyDrawPile");
        }

        return this.cards.poll();
    }
}
