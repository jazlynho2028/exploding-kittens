package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static domain.GameConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    @ParameterizedTest(name = "Constructor for {0} players should produce a deck of size {1} with {2} defuses")
    @CsvSource({
            "2, 59, 3",
            "4, 57, 1"
    })
    void constructor_ValidPlayerCounts_PopulatesCardDeckCorrectly(int numPlayers, int expectedDeckSize, int expectedNumDefuses) {
        DeckBuilder builder = new DeckBuilder(numPlayers);
        Deck deck = builder.getDeck();

        assertNotNull(deck);
        assertEquals(expectedDeckSize, deck.size());

        int actualNumDefuses = countCards(deck, CardType.DEFUSE);
        assertEquals(expectedNumDefuses, actualNumDefuses);
    }

    @ParameterizedTest(name = "Verify card type breakdown for {0} players")
    @ValueSource(ints = {2, 4})
    void constructor_ValidPlayerCounts_PopulatesExactCardTypeQuantities(int numPlayers) {
        DeckBuilder builder = new DeckBuilder(numPlayers);
        Deck deck = builder.getDeck();

        assertBaseCardQuantities(deck);
    }

    @Test
    void calculateDefusesToAdd_NegativeDefuses_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.calculateDefusesToAdd(INVALID_PLAYER_COUNT);
        });
    }

    @Test
    void calculateDefusesToAdd_MinimumPlayers_ReturnsThree() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MIN_PLAYERS);
        assertEquals(EXPECTED_DEFUSE_COUNT_2_PLAYERS, numDefuses);
    }

    @Test
    void calculateDefusesToAdd_MaximumPlayers_ReturnsOne() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MAX_PLAYERS);
        assertEquals(EXPECTED_DEFUSE_COUNT_4_PLAYERS, numDefuses);
    }

    private int countCards(Deck deck, CardType type){
        int count = 0;
        for (Card card : deck.peekTopNCards(deck.size())){
            if (card.getType() == type){
                count++;
            }
        }
        return count;
    }

    private void assertBaseCardQuantities(Deck deck) {
        // 1-Count Cards
        assertEquals(NUM_MILD_DRAW, countCards(deck, CardType.MILD_DRAW));
        assertEquals(NUM_GODCAT, countCards(deck, CardType.GODCAT));
        assertEquals(NUM_WINNER_WINNER_CATNIP_DINNER, countCards(deck, CardType.WINNER_WINNER_CATNIP_DINNER));
        assertEquals(NUM_RAGEBAIT, countCards(deck, CardType.RAGEBAIT));
        assertEquals(NUM_RECYCLE, countCards(deck, CardType.RECYCLE));
        assertEquals(NUM_DOUBLE_UP, countCards(deck, CardType.DOUBLE_UP));
        assertEquals(NUM_CATOMIC_BOMB, countCards(deck, CardType.CATOMIC_BOMB));

        // 2-Count Cards
        assertEquals(NUM_SUPER_SKIP, countCards(deck, CardType.SUPER_SKIP));

        // 3-Count Cards
        assertEquals(NUM_ATTACK, countCards(deck, CardType.ATTACK));
        assertEquals(NUM_SKIP, countCards(deck, CardType.SKIP));
        assertEquals(NUM_CLONE, countCards(deck, CardType.CLONE));
        assertEquals(NUM_SWAP_TOP_AND_BOTTOM, countCards(deck, CardType.SWAP_TOP_AND_BOTTOM));
        assertEquals(NUM_DRAW_FROM_THE_BOTTOM, countCards(deck, CardType.DRAW_FROM_THE_BOTTOM));

        // 4-Count Cards
        assertEquals(NUM_FERAL_CAT, countCards(deck, CardType.FERAL_CAT));
        assertEquals(NUM_SEE_THE_FUTURE, countCards(deck, CardType.SEE_THE_FUTURE));
        assertEquals(NUM_SHUFFLE, countCards(deck, CardType.SHUFFLE));
        assertEquals(NUM_TARGETED_ATTACK, countCards(deck, CardType.TARGETED_ATTACK));
        assertEquals(NUM_CAT_CARD, countCards(deck, CardType.CAT_CARD_1));
        assertEquals(NUM_CAT_CARD, countCards(deck, CardType.CAT_CARD_2));
        assertEquals(NUM_CAT_CARD, countCards(deck, CardType.CAT_CARD_3));
        assertEquals(NUM_CAT_CARD, countCards(deck, CardType.CAT_CARD_4));

        // Should start with 0 EXPLODING_KITTEN in deck
        assertEquals(0, countCards(deck, CardType.EXPLODING_KITTEN));
    }

}