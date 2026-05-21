package domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>();

    public int getCardCount() {
        return this.cards.size(); // Returns collection size [cite: 182]
    }

    public Card removeTop() {
        return null;
    }

    public void addCard(Card card) {

    }

    public void shuffle() {

    }

    public int getCountOfCardType(CardType type) {
        return 0;
    }
}
