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
            baseCards.add(new Card(CardType.RAISINGHECK));
        }

        for (int i = 0; i < 3; i++) {
            baseCards.add(new Card(CardType.ATTACK));
            baseCards.add(new Card(CardType.SKIP));
            baseCards.add(new Card(CardType.CLONE));
            baseCards.add(new Card(CardType.SWAPTOPANDBOTTOM));
            baseCards.add(new Card(CardType.DRAWFROMTHEBOTTOM));
        }

        for (int i = 0; i < 4; i++) {
            baseCards.add(new Card(CardType.NOPE));
            baseCards.add(new Card(CardType.SEETHEFUTURE));
            baseCards.add(new Card(CardType.SHUFFLE));
            baseCards.add(new Card(CardType.TARGETEDATTACK));
        }

        for (int i = 0; i < 16; i++) {
            baseCards.add(new Card(CardType.CATCARD));
        }

        baseCards.add(new Card(CardType.CATATOMICBOMB));
        baseCards.add(new Card(CardType.GODCAT));
        baseCards.add(new Card(CardType.WINNERWINNERCATNIPDINNER));
        baseCards.add(new Card(CardType.RAGEBAIT));
        baseCards.add(new Card(CardType.RECYCLE));
        baseCards.add(new Card(CardType.DOUBLEUP));
        baseCards.add(new Card(CardType.CLEANUP));

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
