package domain;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {
    public static Deck buildDeckWithoutExplodeAndDefuse(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }

        List<Card> cards = initializeFullDeck();

        return new Deck(cards);
    }

    static List<Card> initializeFullDeck() {
        List<Card> baseCards = new ArrayList<>();

        // Temporarily fill deck with ATTACK cards until metadata parsing is added
        for (int i = 0; i < 56; i++) {
            baseCards.add(new Card(CardType.ATTACK));
        }
        return baseCards;
    }
}
