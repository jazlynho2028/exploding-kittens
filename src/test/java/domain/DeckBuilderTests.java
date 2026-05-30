package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.GameConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    private List<Card> baseCards;

    @BeforeEach
    void setUp() {
        DeckBuilder builder = new DeckBuilder();
        baseCards = builder.initializeDeckWithoutDefuses();
    }

    @Test
    void initializeDeckWithoutDefuses_TotalCardCount_EqualsBaselineConstant() {
        assertEquals(56, baseCards.size());
    }

    @Test
    void initializeDeckWithoutDefuses_SingleInstanceCards_PopulateCorrectQuantitiesAndIDs() {
        verifyCardTypeGroup(baseCards, CardType.MILD_DRAW, NUM_MILD_DRAW, "MILDDRAW");
        verifyCardTypeGroup(baseCards, CardType.GODCAT, NUM_GODCAT, "GODCAT");
        verifyCardTypeGroup(baseCards, CardType.WINNER_WINNER_CATNIP_DINNER, NUM_WINNER_WINNER_CATNIP_DINNER, "WINNERWINNERCATNIPDINNER");
        verifyCardTypeGroup(baseCards, CardType.RAGEBAIT, NUM_RAGEBAIT, "RAGEBAIT");
        verifyCardTypeGroup(baseCards, CardType.RECYCLE, NUM_RECYCLE, "RECYCLE");
        verifyCardTypeGroup(baseCards, CardType.DOUBLE_UP, NUM_DOUBLE_UP, "DOUBLEUP");
        verifyCardTypeGroup(baseCards, CardType.CATOMIC_BOMB, NUM_CATOMIC_BOMB, "CATOMICBOMB");
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
    void calculateDefusesToAdd_MaximumPlayers_ReturnsOne(){
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MAX_PLAYERS);
        assertEquals(EXPECTED_DEFUSE_COUNT_4_PLAYERS, numDefuses);
    }

    @Test
    void createCardID_LowerValidInput_ReturnsCorrectString(){
        String actualID = DeckBuilder.createCardId(CardType.FERAL_CAT, 1);
        assertEquals("FERALCAT_1", actualID);
    }

    @Test
    void createCardID_UpperValidInput_ReturnsCorrectString(){
        String actualID = DeckBuilder.createCardId(CardType.ATTACK, 3);
        assertEquals("ATTACK_3", actualID);
    }

    @Test
    void createCardId_ZeroSequenceNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.createCardId(CardType.MILD_DRAW, 0);
        });
    }


    private void verifyCardTypeGroup(List<Card> cards, CardType type, int expectedQuantity, String idPrefix) {
        int matchCount = 0;
        for (Card card : cards) {
            if (card.getType() == type) {
                matchCount++;
                String expectedId = String.format("%s_%d", idPrefix, matchCount);
                assertEquals(expectedId, card.getId());
            }
        }
        assertEquals(expectedQuantity, matchCount);
    }
}