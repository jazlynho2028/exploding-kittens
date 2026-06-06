package domain;

import java.util.*;

import static domain.GameConstants.*;

public class DeckBuilder {

    public DeckBuilder() {
    }

    public Deck initializeDeck(int numPlayers) {
        List<Card> cardsList = initializeDeckWithoutDefuses();

        int defusesToAdd = calculateDefusesToAdd(numPlayers);
        addCards(cardsList, CardType.DEFUSE, defusesToAdd);

        return createDeckInstance(cardsList);
    }

    Deck createDeckInstance(List<Card> cardsList) {
        Deque<Card> deckDeque = new ArrayDeque<>(cardsList);
        Deck baseDeck = new Deck(deckDeque, new Random());
        baseDeck.shuffle();

        return baseDeck;
    }

    List<Card> initializeDeckWithoutDefuses() {
        List<Card> cardsList = new ArrayList<>();

        addCards(cardsList, CardType.MILD_SHUFFLE, NUM_MILD_SHUFFLE_IN_GAME);
        addCards(cardsList, CardType.GODCAT, NUM_GODCAT_IN_GAME);
        addCards(cardsList, CardType.WINNER_WINNER_CATNIP_DINNER,
                NUM_WINNER_WINNER_CATNIP_DINNER_IN_GAME);
        addCards(cardsList, CardType.RAGEBAIT, NUM_RAGEBAIT_IN_GAME);
        addCards(cardsList, CardType.RECYCLE, NUM_RECYCLE_IN_GAME);
        addCards(cardsList, CardType.DOUBLE_UP, NUM_DOUBLE_UP_IN_GAME);
        addCards(cardsList, CardType.CATOMIC_BOMB, NUM_CATOMIC_BOMB_IN_GAME);
        addCards(cardsList, CardType.SUPER_SKIP, NUM_SUPER_SKIP_IN_GAME);
        addCards(cardsList, CardType.ATTACK, NUM_ATTACK_IN_GAME);
        addCards(cardsList, CardType.SKIP, NUM_SKIP_IN_GAME);
        addCards(cardsList, CardType.CLONE, NUM_CLONE_IN_GAME);
        addCards(cardsList, CardType.SWAP_TOP_AND_BOTTOM, NUM_SWAP_TOP_AND_BOTTOM_IN_GAME);
        addCards(cardsList, CardType.DRAW_FROM_THE_BOTTOM, NUM_DRAW_FROM_THE_BOTTOM_IN_GAME);
        addCards(cardsList, CardType.FERAL_CAT, NUM_FERAL_CAT_IN_GAME);
        addCards(cardsList, CardType.SEE_THE_FUTURE, NUM_SEE_THE_FUTURE_IN_GAME);
        addCards(cardsList, CardType.SHUFFLE, NUM_SHUFFLE_IN_GAME);
        addCards(cardsList, CardType.TARGETED_ATTACK, NUM_TARGETED_ATTACK_IN_GAME);
        addCards(cardsList, CardType.CAT_CARD_1, NUM_CAT_CARD_IN_GAME);
        addCards(cardsList, CardType.CAT_CARD_2, NUM_CAT_CARD_IN_GAME);
        addCards(cardsList, CardType.CAT_CARD_3, NUM_CAT_CARD_IN_GAME);
        addCards(cardsList, CardType.CAT_CARD_4, NUM_CAT_CARD_IN_GAME);

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
            throw new IllegalArgumentException("error.invalidCardIDNumber");
        }

        String cardTypeNameWithoutUnderscore = type.name().replace("_", "");

        return String.format(
                "%s_%d",
                cardTypeNameWithoutUnderscore,
                num
        );
    }
}
