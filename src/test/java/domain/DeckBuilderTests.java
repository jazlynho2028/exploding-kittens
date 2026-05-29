package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {

    @Test
    void buildDeck_MinAllowedPlayers_ReturnsCorrect59CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(2);
        assertEquals(59, deck.size());

        int defuseCount = 0;
        int initialSize = deck.size();
        for (int i = 0; i < initialSize; i++) {
            Card card = deck.removeTop();
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(3, defuseCount, "2 players should result in 3 defuse cards in the deck");
    }

    @Test
    void buildDeck_MaxAllowedPlayers_ReturnsCorrect57CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(4);
        assertEquals(57, deck.size());

        int defuseCount = 0;
        int initialSize = deck.size();
        for (int i = 0; i < initialSize; i++) {
            Card card = deck.removeTop();
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(1, defuseCount, "4 players should result in 1 defuse card in the deck");
    }

    @Test
    void initializeFullDeck_ReturnsExactly56BaseCards() {
        List<Card> baseDeck = DeckBuilder.initializeFullDeck();

        assertNotNull(baseDeck, "The returned card list should not be null");
        assertEquals(56, baseDeck.size(), "The base deck should start with 56 cards");

        for (Card card : baseDeck) {
            assertNotEquals(CardType.EXPLODING_KITTEN, card.getType());
            assertNotEquals(CardType.DEFUSE, card.getType());
        }
    }

    @Test
    void calculateDefusesToAdd_NegativeDefuses_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.calculateDefusesToAdd(6);
        });
    }

    @Test
    void calculateDefusesToAdd_MinimumPlayers_ReturnsThree() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(2);
        assertEquals(3, numDefuses, "2 players should leave 3 defuses in the deck (5 total - 2 dealt)");
    }

    @Test
    void calculateDefusesToAdd_MaximumPlayers_ReturnsOne() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(4);
        assertEquals(1, numDefuses, "4 players should leave 1 defuse in the deck (5 total - 4 dealt)");
    }

}