package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {

    private static List<Card> cards;

    public static Deck buildDeckWithoutExplodeAndAddDefuse(int numPlayers) {
        Deck deck = initializeFullDeck();

        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addCards(CardType.DEFUSE, defusesToAdd);

        deck.shuffle();

        return deck;
    }

    static Deck initializeFullDeck() {
        cards = new ArrayList<>();

        addCards(CardType.MILD_DRAW, 1);
        addCards(CardType.GODCAT, 1);
        addCards(CardType.WINNER_WINNER_CATNIP_DINNER, 1);
        addCards(CardType.RAGEBAIT, 1);
        addCards(CardType.RECYCLE, 1);
        addCards(CardType.DOUBLE_UP, 1);
        addCards(CardType.CATOMIC_BOMB, 1);

        addCards(CardType.SUPER_SKIP, 2);

        addCards(CardType.ATTACK, 3);
        addCards(CardType.SKIP, 3);
        addCards(CardType.CLONE, 3);
        addCards(CardType.SWAP_TOP_AND_BOTTOM, 3);
        addCards(CardType.DRAW_FROM_THE_BOTTOM, 3);

        addCards(CardType.FERAL_CAT, 4);
        addCards(CardType.SEE_THE_FUTURE, 4);
        addCards(CardType.SHUFFLE, 4);
        addCards(CardType.TARGETED_ATTACK, 4);
        addCards(CardType.CAT_CARD_1, 4);
        addCards(CardType.CAT_CARD_2, 4);
        addCards(CardType.CAT_CARD_3, 4);
        addCards(CardType.CAT_CARD_4, 4);

        return new Deck(new ArrayDeque<>(cards));
    }

    private static void addCards(CardType type, int numToAdd) {
        for (int i = 1; i <= numToAdd; i++) {
            String cardId = createCardId(type, i);
            cards.add(new Card(cardId, type));
        }
    }

    public static String createCardId(CardType type, int num) {
        String test = type.name().replace("_", "") + "_" + num;
        System.out.println("HERE: "+test);
        return test;
    }

    static int calculateDefusesToAdd(int numPlayers) {
        int defusesToAdd = 5 - numPlayers;

        if (defusesToAdd < 0) {
            throw new IllegalArgumentException("Cannot add negative number of defuses");
        }

        return defusesToAdd;
    }

}
