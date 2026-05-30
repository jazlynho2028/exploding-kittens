package domain;

import java.util.Deque;
import java.util.List;
import java.util.Random;

public class Deck {

    public Deck(Deque<Card> deck) { }

    Deck(Deque<Card> deck, Random random) { }

    public void shuffle() { }

    public Card peekTop() {
        return new Card("placeholder", CardType.DEFUSE);
    }

    public Card removeTop() {
        return new Card("placeholder", CardType.DEFUSE);
    }

    public int size() {
        return 0;
    }

    public Card peekBottom() {
        return new Card("placeholder", CardType.DEFUSE);
    }

    public List<Card> peekTopNCards(int n) {
        return List.of();
    }

    public Card removeBottom() {
        return new Card("placeholder", CardType.DEFUSE);
    }

    public void addCard(Card card) { }

    public boolean isEmpty() {
        return true;
    }

    List<Card> getCards() {
        return List.of();
    }
}