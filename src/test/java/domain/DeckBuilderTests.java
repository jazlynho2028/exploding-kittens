package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

}