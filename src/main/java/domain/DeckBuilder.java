package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static domain.GameConstants.*;

public class DeckBuilder {

    private final Deck deck;

    public DeckBuilder(int numPlayers) {
        this.deck = buildPlayableDeck(numPlayers);
    }

    public Deck getDeck() {
        return this.deck;
    }

    private Deck buildPlayableDeck(int numPlayers) {
        List<Card> cardsList = initializeDeckWithoutDefuses();

        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addCards(cardsList, CardType.DEFUSE, defusesToAdd);

        Deque<Card> deckDeque = new ArrayDeque<>(cardsList);
        Deck baseDeck = new Deck(deckDeque);
        baseDeck.shuffle();

        return baseDeck;
    }

    private List<Card> initializeDeckWithoutDefuses() {
        List<Card> cardsList = new ArrayList<>();

        addCards(cardsList, CardType.MILD_DRAW, NUM_MILD_DRAW);
        addCards(cardsList, CardType.GODCAT, NUM_GODCAT);
        addCards(cardsList, CardType.WINNER_WINNER_CATNIP_DINNER, NUM_WINNER_WINNER_CATNIP_DINNER);
        addCards(cardsList, CardType.RAGEBAIT, NUM_RAGEBAIT);
        addCards(cardsList, CardType.RECYCLE, NUM_RECYCLE);
        addCards(cardsList, CardType.DOUBLE_UP, NUM_DOUBLE_UP);
        addCards(cardsList, CardType.CATOMIC_BOMB, NUM_CATOMIC_BOMB);
        addCards(cardsList, CardType.SUPER_SKIP, NUM_SUPER_SKIP);
        addCards(cardsList, CardType.ATTACK, NUM_ATTACK);
        addCards(cardsList, CardType.SKIP, NUM_SKIP);
        addCards(cardsList, CardType.CLONE, NUM_CLONE);
        addCards(cardsList, CardType.SWAP_TOP_AND_BOTTOM, NUM_SWAP_TOP_AND_BOTTOM);
        addCards(cardsList, CardType.DRAW_FROM_THE_BOTTOM, NUM_DRAW_FROM_THE_BOTTOM);
        addCards(cardsList, CardType.FERAL_CAT, NUM_FERAL_CAT);
        addCards(cardsList, CardType.SEE_THE_FUTURE, NUM_SEE_THE_FUTURE);
        addCards(cardsList, CardType.SHUFFLE, NUM_SHUFFLE);
        addCards(cardsList, CardType.TARGETED_ATTACK, NUM_TARGETED_ATTACK);
        addCards(cardsList, CardType.CAT_CARD_1, NUM_CAT_CARD);
        addCards(cardsList, CardType.CAT_CARD_2, NUM_CAT_CARD);
        addCards(cardsList, CardType.CAT_CARD_3, NUM_CAT_CARD);
        addCards(cardsList, CardType.CAT_CARD_4, NUM_CAT_CARD);

        return cardsList;
    }

    private void addCards(List<Card> destinationList, CardType type, int numToAdd) {
        for (int i = 1; i <= numToAdd; i++) {
            String cardId = createCardId(type, i);
            destinationList.add(new Card(cardId, type));
        }
    }

     static int calculateDefusesToAdd(int numPlayers) {
        int defusesToAdd = NUM_DEFUSES_IN_GAME - numPlayers;

        if (defusesToAdd < 0) {
            throw new IllegalArgumentException("error.negativeDefuseCount");
        }

        return defusesToAdd;
    }

    public static String createCardId(CardType type, int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("error.invalidCardSequenceNumber");
        }

        String cardTypeNameWithoutUnderscore = type.name().replace("_", "");

        return String.format(
                "%s_%d",
                cardTypeNameWithoutUnderscore,
                num
        );
    }
}