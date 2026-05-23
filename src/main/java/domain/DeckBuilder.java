package domain;

import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {
    public static Deck buildDeckWithoutExplodeAndAddDefuse(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }

        List<Card> deck = initializeFullDeck();
        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addPlayerDefuses(deck, defusesToAdd);
        shuffleDeck(deck);

        return new Deck(deck);
    }

    static List<Card> initializeFullDeck() {
        List<Card> baseCards = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            baseCards.add(new Card(CardType.SUPER_SKIP));
        }

        for (int i = 0; i < 3; i++) {
            baseCards.add(new Card(CardType.ATTACK));
            baseCards.add(new Card(CardType.SKIP));
            baseCards.add(new Card(CardType.CLONE));
            baseCards.add(new Card(CardType.SWAP_TOP_AND_BOTTOM));
            baseCards.add(new Card(CardType.DRAW_FROM_THE_BOTTOM));
        }

        for (int i = 0; i < 4; i++) {
            baseCards.add(new Card(CardType.FERAL_CAT));
            baseCards.add(new Card(CardType.SEE_THE_FUTURE));
            baseCards.add(new Card(CardType.SHUFFLE));
            baseCards.add(new Card(CardType.TARGETED_ATTACK));
            baseCards.add(new Card(CardType.CAT_CARD_1));
            baseCards.add(new Card(CardType.CAT_CARD_2));
            baseCards.add(new Card(CardType.CAT_CARD_3));
            baseCards.add(new Card(CardType.CAT_CARD_4));
        }

        baseCards.add(new Card(CardType.MILD_DRAW));
        baseCards.add(new Card(CardType.GODCAT));
        baseCards.add(new Card(CardType.WINNER_WINNER_CATNIP_DINNER));
        baseCards.add(new Card(CardType.RAGEBAIT));
        baseCards.add(new Card(CardType.RECYCLE));
        baseCards.add(new Card(CardType.DOUBLE_UP));
        baseCards.add(new Card(CardType.CATOMIC_BOMB));

        return baseCards;
    }

    static int calculateDefusesToAdd(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }

        return 5 - numPlayers;
    }

    static void addPlayerDefuses(List<Card> deck, int defuseCount) {
        for (int i = 0; i < defuseCount; i++) {
            deck.add(new Card(CardType.DEFUSE));
        }
    }

    static void shuffleDeck(List<Card> deck) {
        java.util.Collections.shuffle(deck);
    }
}
