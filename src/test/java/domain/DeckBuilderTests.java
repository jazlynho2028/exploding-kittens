package domain;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.GameConstants.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    private List<Card> baseCards;

    @BeforeEach
    void setUp() {
        DeckBuilder builder = new DeckBuilder();
        baseCards = builder.initializeDeckWithoutDefuses();
    }

    @Test
    void initializeDeck_MinimumPlayers_AppendsThreeDefuses() {
        Deck mockDeck = mock(Deck.class);
        DeckBuilder deckBuilder = EasyMock.createMockBuilder(DeckBuilder.class)
                .addMockedMethod("createDeckInstance")
                .createMock();

        Capture<List<Card>> capturedList = EasyMock.newCapture();
        expect(deckBuilder.createDeckInstance(capture(capturedList))).andReturn(mockDeck);
        replay(mockDeck, deckBuilder);

        Deck resultDeck = deckBuilder.initializeDeck(MIN_PLAYERS);

        assertSame(mockDeck, resultDeck);
        List<Card> finalAssembledCards = capturedList.getValue();

        assertEquals(EXPECTED_DECK_SIZE_2_PLAYERS, finalAssembledCards.size());

        verifyCardTypeGroup(finalAssembledCards, CardType.DEFUSE, EXPECTED_DEFUSE_COUNT_2_PLAYERS, "DEFUSE");

        verify(mockDeck, deckBuilder);
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
    void initializeDeckWithoutDefuses_DoubleInstanceCards_PopulateCorrectQuantitiesAndIDs() {
        verifyCardTypeGroup(baseCards, CardType.SUPER_SKIP, NUM_SUPER_SKIP, "SUPERSKIP");
    }

    @Test
    void initializeDeckWithoutDefuses_TripleInstanceCards_PopulateCorrectQuantitiesAndIDs() {
        verifyCardTypeGroup(baseCards, CardType.ATTACK, NUM_ATTACK, "ATTACK");
        verifyCardTypeGroup(baseCards, CardType.SKIP, NUM_SKIP, "SKIP");
        verifyCardTypeGroup(baseCards, CardType.CLONE, NUM_CLONE, "CLONE");
        verifyCardTypeGroup(baseCards, CardType.SWAP_TOP_AND_BOTTOM, NUM_SWAP_TOP_AND_BOTTOM, "SWAPTOPANDBOTTOM");
        verifyCardTypeGroup(baseCards, CardType.DRAW_FROM_THE_BOTTOM, NUM_DRAW_FROM_THE_BOTTOM, "DRAWFROMTHEBOTTOM");
    }

    @Test
    void initializeDeckWithoutDefuses_QuadrupleInstanceCards_PopulateCorrectQuantitiesAndIDs() {
        verifyCardTypeGroup(baseCards, CardType.FERAL_CAT, NUM_FERAL_CAT, "FERALCAT");
        verifyCardTypeGroup(baseCards, CardType.SEE_THE_FUTURE, NUM_SEE_THE_FUTURE, "SEETHEFUTURE");
        verifyCardTypeGroup(baseCards, CardType.SHUFFLE, NUM_SHUFFLE, "SHUFFLE");
        verifyCardTypeGroup(baseCards, CardType.TARGETED_ATTACK, NUM_TARGETED_ATTACK, "TARGETEDATTACK");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_1, NUM_CAT_CARD, "CATCARD1");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_2, NUM_CAT_CARD, "CATCARD2");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_3, NUM_CAT_CARD, "CATCARD3");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_4, NUM_CAT_CARD, "CATCARD4");
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
    void createCardID_LowerValidInput_ReturnsCorrectString() {
        String actualID = DeckBuilder.createCardId(CardType.FERAL_CAT, 1);
        assertEquals("FERALCAT_1", actualID);
    }

    @Test
    void createCardID_UpperValidInput_ReturnsCorrectString() {
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