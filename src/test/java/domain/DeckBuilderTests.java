package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeckBuilderTests {
    @Test
    void buildDeck_LessThanMinPlayers_ThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.buildDeckWithoutExplodeAndDefuse(1);
        });
    }
}
