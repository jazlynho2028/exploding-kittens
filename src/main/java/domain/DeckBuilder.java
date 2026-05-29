package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static domain.GameConstants.*;

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

        addCards(CardType.MILD_DRAW, ONE_CARD);
        addCards(CardType.GODCAT, ONE_CARD);
        addCards(CardType.WINNER_WINNER_CATNIP_DINNER, ONE_CARD);
        addCards(CardType.RAGEBAIT, ONE_CARD);
        addCards(CardType.RECYCLE, ONE_CARD);
        addCards(CardType.DOUBLE_UP, ONE_CARD);
        addCards(CardType.CATOMIC_BOMB, ONE_CARD);

        addCards(CardType.SUPER_SKIP, TWO_CARDS);

        addCards(CardType.ATTACK, THREE_CARDS);
        addCards(CardType.SKIP, THREE_CARDS);
        addCards(CardType.CLONE, THREE_CARDS);
        addCards(CardType.SWAP_TOP_AND_BOTTOM, THREE_CARDS);
        addCards(CardType.DRAW_FROM_THE_BOTTOM, THREE_CARDS);

        addCards(CardType.FERAL_CAT, FOUR_CARDS);
        addCards(CardType.SEE_THE_FUTURE, FOUR_CARDS);
        addCards(CardType.SHUFFLE, FOUR_CARDS);
        addCards(CardType.TARGETED_ATTACK, FOUR_CARDS);
        addCards(CardType.CAT_CARD_1, FOUR_CARDS);
        addCards(CardType.CAT_CARD_2, FOUR_CARDS);
        addCards(CardType.CAT_CARD_3, FOUR_CARDS);
        addCards(CardType.CAT_CARD_4, FOUR_CARDS);

        return new Deck(new ArrayDeque<>(cards));
    }

    private static void addCards(CardType type, int numToAdd) {
        for (int i = 1; i <= numToAdd; i++) {
            String cardId = createCardId(type, i);
            cards.add(new Card(cardId, type));
        }
    }

    public static String createCardId(CardType type, int num) {
        return type.name().replace("_", "") + "_" + num;
    }

    static int calculateDefusesToAdd(int numPlayers) {
        int defusesToAdd = NUM_DEFUSES - numPlayers;

        if (defusesToAdd < 0) {
            throw new IllegalArgumentException("Cannot add negative number of defuses");
        }

        return defusesToAdd;
    }

}
