package domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck(Deck otherDeck) {
        if (otherDeck == null) {
            this.cards = new ArrayList<>();
        }
        else {
            this.cards = new ArrayList<>(otherDeck.cards);
        }
    }

    public int getCardCount() {
        return this.cards.size();
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
