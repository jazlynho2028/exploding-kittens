package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void buildDeck_MinAllowedPlayers_DoesNotReturnNull() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndDefuse(2);
        assertNotNull(deck, "We have a valid number of players, so deck should not be null");
    }
}
