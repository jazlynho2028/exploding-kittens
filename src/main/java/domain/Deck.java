package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Deck {
    private static final String ERROR_EMPTY_DECK = "error.emptyDeck";

    private final Deque<Card> deck;
    private final Random random;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "Deck intentionally stores injected mutable dependencies " +
                    "to support controlled deck state and deterministic shuffle testing."
    )
    public Deck(Deque<Card> deck, Random random) {
        this.deck = deck;
        this.random = random;
    }

    public void shuffle() {
        List<Card> cards = new ArrayList<>(deck);

        for (int i = cards.size() - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            Card currentCard = cards.get(i);
            cards.set(i, cards.get(randomIndex));
            cards.set(randomIndex, currentCard);
        }

        deck.clear();
        deck.addAll(cards);
    }

    public Card peekTop() {
        checkNotEmpty();

        return deck.peekFirst();
    }

    public Card removeTop() {
        checkNotEmpty();

        return deck.removeFirst();
    }

    public int size() {
        return deck.size();
    }

    private void checkNotEmpty() {
        if (deck.isEmpty()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }
    }

    public Card peekBottom() {
        checkNotEmpty();

        return deck.peekLast();
    }

    public List<Card> peekTopNCards(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot peek a negative number of cards.");
        }

        if (n > deck.size()) {
            throw new IllegalStateException(ERROR_EMPTY_DECK);
        }

        List<Card> cards = new ArrayList<>(deck);
        return new ArrayList<>(cards.subList(0, n));
    }

    public Card removeBottom() {
        checkNotEmpty();

        return deck.removeLast();
    }

    public void addCard(Card card) {
        deck.addLast(card);
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    List<Card> getCards() {
        return List.copyOf(deck);
    }
}