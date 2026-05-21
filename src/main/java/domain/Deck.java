package domain;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> cards = new ArrayList<>();

    public int getCardCount() {
        return this.cards.size(); // Returns collection size [cite: 182]
    }
}
