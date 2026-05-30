package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static domain.GameConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {

    @Test
    void buildDeck_NumberOfPlayers_ReturnsCorrectCardDeck(int numPlayers, int expectedDeckSize) {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(MIN_PLAYERS);
        assertEquals(EXPECTED_DECK_SIZE_2_PLAYERS, deck.size());

        int defuseCount = 0;
        int initialSize = deck.size();
        for (int i = 0; i < initialSize; i++) {
            Card card = deck.removeTop();
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(EXPECTED_DEFUSE_COUNT_2_PLAYERS, defuseCount);
    }

    @Test
    void buildDeck_MaxAllowedPlayers_ReturnsCorrect57CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(MAX_PLAYERS);
        assertEquals(EXPECTED_DECK_SIZE_4_PLAYERS, deck.size());

        int defuseCount = 0;
        int initialSize = deck.size();
        for (int i = 0; i < initialSize; i++) {
            Card card = deck.removeTop();
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(EXPECTED_DEFUSE_COUNT_4_PLAYERS, defuseCount);
    }

    @Test
    void initializeFullDeck_ReturnsExactly56BaseCards() {
        List<Card> baseDeck = DeckBuilder.initializeFullDeck();

        assertNotNull(baseDeck);
        assertEquals(EXPECTED_BASE_DECK_SIZE, baseDeck.size());

        for (Card card : baseDeck) {
            assertNotEquals(CardType.EXPLODING_KITTEN, card.getType());
            assertNotEquals(CardType.DEFUSE, card.getType());
        }
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

    @Test
    void constructor_MinimumPlayers_PopulatesCorrect59CardDeck() {
        DeckBuilder builder = new DeckBuilder(MIN_PLAYERS);
        Deck deck = builder.getDeck();

        assertNotNull(deck);
        assertEquals(59, deck.size());
    }

}