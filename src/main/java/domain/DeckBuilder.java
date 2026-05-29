package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DeckBuilder {

    public static Deck buildDeckWithoutExplodeAndAddDefuse(int numPlayers) {
        List<Card> cardsList = initializeFullDeck();

        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addCards(cardsList, CardType.DEFUSE, defusesToAdd);

        Deque<Card> deckDeque = new ArrayDeque<>(cardsList);
        Deck deck = new Deck(deckDeque);

        deck.shuffle();

        return deck;
    }

    static List<Card> initializeFullDeck() {
        List<Card> cardsList = new ArrayList<>();

        addCards(cardsList, CardType.MILD_DRAW, 1);
        addCards(cardsList, CardType.GODCAT, 1);
        addCards(cardsList, CardType.WINNER_WINNER_CATNIP_DINNER, 1);
        addCards(cardsList, CardType.RAGEBAIT, 1);
        addCards(cardsList, CardType.RECYCLE, 1);
        addCards(cardsList, CardType.DOUBLE_UP, 1);
        addCards(cardsList, CardType.CATOMIC_BOMB, 1);

        addCards(cardsList, CardType.SUPER_SKIP, 2);

        addCards(cardsList, CardType.ATTACK, 3);
        addCards(cardsList, CardType.SKIP, 3);
        addCards(cardsList, CardType.CLONE, 3);
        addCards(cardsList, CardType.SWAP_TOP_AND_BOTTOM, 3);
        addCards(cardsList, CardType.DRAW_FROM_THE_BOTTOM, 3);

        addCards(cardsList, CardType.FERAL_CAT, 4);
        addCards(cardsList, CardType.SEE_THE_FUTURE, 4);
        addCards(cardsList, CardType.SHUFFLE, 4);
        addCards(cardsList, CardType.TARGETED_ATTACK, 4);
        addCards(cardsList, CardType.CAT_CARD_1, 4);
        addCards(cardsList, CardType.CAT_CARD_2, 4);
        addCards(cardsList, CardType.CAT_CARD_3, 4);
        addCards(cardsList, CardType.CAT_CARD_4, 4);

        return cardsList;
    }

    private static void addCards(List<Card> destinationList, CardType type, int numToAdd) {
        for (int i = 1; i <= numToAdd; i++) {
            String cardId = createCardId(type, i);
            destinationList.add(new Card(cardId, type));
        }
    }

    public static String createCardId(CardType type, int num) {
        String cardTypeNameWithoutUnderscore = type.name().replace("_", "");

        return String.format(
                "%s_%d",
                cardTypeNameWithoutUnderscore,
                num
        );
    }

    static int calculateDefusesToAdd(int numPlayers) {
        int defusesToAdd = 5 - numPlayers;

        if (defusesToAdd < 0) {
            throw new IllegalArgumentException("error.negativeDefuseCount");
        }

        return defusesToAdd;
    }

}