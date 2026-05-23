package domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    @Test
    void buildDeck_LessThanMinPlayers_ThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.buildDeckWithoutExplodeAndDefuse(1);
        });
    }

    @Test
    void buildDeck_MoreThanMaxPlayers_ThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.buildDeckWithoutExplodeAndDefuse(5);
        });
    }

    @Test
    void buildDeck_MinAllowedPlayers_ReturnsCorrect60CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndDefuse(2);
        assertNotNull(deck, "We have a valid number of players, so deck should not be null");

        assertEquals(60, deck.size(), "2 players should result in a 60-card deck");

        int numDefuses = 0;
        for (Card card : deck.getCards()) {
            if (card.getType() == CardType.DEFUSE) {
                numDefuses++;
            }
        }
        assertEquals(4, numDefuses, "2 players should result in 4 defuse cards in the deck");
    }

    @Test
    void initializeFullDeck_ReturnsExactly56BaseCards() {
        List<Card> baseCards = DeckBuilder.initializeFullDeck();

        assertNotNull(baseCards, "The returned card list should not be null");
        assertEquals(56, baseCards.size(), "The base deck should start with 56 cards");

        for (Card card : baseCards) {
            assertNotEquals(CardType.EXPLODINGKITTEN, card.getType());
            assertNotEquals(CardType.DEFUSE, card.getType());
        }
    }

    @Test
    void calculateDefusesToAdd_LessThanMinimumPlayers_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.calculateDefusesToAdd(1);
        });
    }

    @Test
    void calculateDefusesToAdd_MinimumPlayers_ReturnsThree() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(2);
        assertEquals(3, numDefuses, "2 players should leave 3 defuses in the deck (5 total - 2 dealt)");
    }
}
