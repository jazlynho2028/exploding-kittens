package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Deck {
    private static final String ERROR_EMPTY_DECK = "error.emptyDeck";

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
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        return this.deck.peekFirst();
    }

    public Card removeTop() {
        if (this.deck.isEmpty()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        return this.deck.removeFirst();
    }

    public int size() {
        return this.deck.size();
    }

    public Card peekBottom() {
        if (this.deck.isEmpty()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        return this.deck.peekLast();
    }

    public List<Card> peekTopNCards(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot peek a negative number of cards.");
        }

        if (n > this.deck.size()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        List<Card> cards = new ArrayList<>(this.deck);
        return new ArrayList<>(cards.subList(0, n));
    }

    public Card removeBottom() {
        if (this.deck.isEmpty()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        return this.deck.removeLast();
    }

    public void addCard(Card card) {
        this.deck.addLast(card);
    }

    public boolean isEmpty() {
        return this.deck.isEmpty();
    }

    List<Card> getCards() {
        return List.copyOf(this.deck);
    }
}