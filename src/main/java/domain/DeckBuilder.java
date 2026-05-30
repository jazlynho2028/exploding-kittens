package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static domain.GameConstants.*;

public class DeckBuilder {

    private final Deck deck;

    public DeckBuilder(int numPlayers){
        List<Card> cardsList = initializeFullDeck();

        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addCards(cardsList, CardType.DEFUSE, defusesToAdd);

        Deque<Card> deckDeque = new ArrayDeque<>(cardsList);
        this.deck = new Deck(deckDeque);
        this.deck.shuffle();
    }

    public Deck getDeck(){
        return this.deck;
    }

     List<Card> initializeFullDeck() {
        List<Card> cardsList = new ArrayList<>();

        addCards(cardsList, CardType.MILD_DRAW, 1);
        addCards(cardsList, CardType.GODCAT, 1);
        addCards(cardsList, CardType.WINNER_WINNER_CATNIP_DINNER, 1);
        addCards(cardsList, CardType.RAGEBAIT, 1);
        addCards(cardsList, CardType.RECYCLE, 1);
        addCards(cardsList, CardType.DOUBLE_UP, 1);
        addCards(cardsList, CardType.CATOMIC_BOMB, 1);

        addCards(cardsList, CardType.SUPER_SKIP, 2);

        addCards(cardsList, CardType.ATTACK, THREE_CARD_COUNT);
        addCards(cardsList, CardType.SKIP, THREE_CARD_COUNT);
        addCards(cardsList, CardType.CLONE, THREE_CARD_COUNT);
        addCards(cardsList, CardType.SWAP_TOP_AND_BOTTOM, THREE_CARD_COUNT);
        addCards(cardsList, CardType.DRAW_FROM_THE_BOTTOM, THREE_CARD_COUNT);

        addCards(cardsList, CardType.FERAL_CAT, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.SEE_THE_FUTURE, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.SHUFFLE, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.TARGETED_ATTACK, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.CAT_CARD_1, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.CAT_CARD_2, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.CAT_CARD_3, FOUR_CARD_COUNT);
        addCards(cardsList, CardType.CAT_CARD_4, FOUR_CARD_COUNT);

        return cardsList;
    }

    private void addCards(List<Card> destinationList, CardType type, int numToAdd) {
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
        int defusesToAdd = NUM_DEFUSES_IN_GAME - numPlayers;

        if (defusesToAdd < 0) {
            throw new IllegalArgumentException("error.negativeDefuseCount");
        }

        return defusesToAdd;
    }

}