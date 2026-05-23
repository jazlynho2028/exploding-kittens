package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Deck {

    private final Deque<Card> deck;
    private final Random random;

    public Deck(Deque<Card> deck) {
        this(deck, new Random());
    }

    Deck(Deque<Card> deck, Random random) {
        this.deck = new ArrayDeque<>(deck);
        this.random = random;
    }

    public void shuffle() {
        List<Card> cards = new ArrayList<>(this.deck);

        for (int i = cards.size() - 1; i > 0; i--) {
            int randomIndex = this.random.nextInt(i + 1);
            Card currentCard = cards.get(i);
            cards.set(i, cards.get(randomIndex));
            cards.set(randomIndex, currentCard);
        }

        this.deck.clear();
        this.deck.addAll(cards);
    }

    public Card peekTop() {
        if (this.deck.isEmpty()) {
            throw new UnsupportedOperationException("Cannot peek top of empty deck.");
        }

        return this.deck.peekFirst();
    }

    public Card removeTop() {
        if (this.deck.isEmpty()) {
            throw new UnsupportedOperationException("Cannot remove top of empty deck.");
        }

        return this.deck.removeFirst();
    }

    public int size() {
        return this.deck.size();
    }
}