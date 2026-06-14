package domain;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static domain.GameConstants.*;
import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    private List<Card> baseCards;

    @BeforeEach
    public void setUp() {
        Random mockRandom = EasyMock.createMock(Random.class);
        DeckBuilder builder = new DeckBuilder(mockRandom);
        baseCards = builder.initializeDeckWithoutDefuses();
    }

    @Test
    public void initializeDeck_MinimumPlayers_AppendsThreeDefuses() {
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

        int expectedDeckSizeWithoutDefuses = getExpectedDeckSizeWithoutDefuses();
        int expectedDeckSizeAddDefuses = expectedDeckSizeWithoutDefuses
                + (NUM_DEFUSES_IN_GAME - MIN_PLAYERS);
        assertEquals(expectedDeckSizeAddDefuses, finalAssembledCards.size());

        verifyCardTypeGroup(
                finalAssembledCards,
                CardType.DEFUSE,
                NUM_DEFUSES_IN_GAME - MIN_PLAYERS,
                "DEFUSE");

        verify(mockDeck, deckBuilder);
    }

    @Test
    public void initializeDeck_MaximumPlayers_AppendsOneDefuse() {
        Deck mockDeck = mock(Deck.class);
        DeckBuilder deckBuilder = EasyMock.createMockBuilder(DeckBuilder.class)
                .addMockedMethod("createDeckInstance")
                .createMock();

        Capture<List<Card>> capturedList = EasyMock.newCapture();
        expect(deckBuilder.createDeckInstance(capture(capturedList))).andReturn(mockDeck);
        replay(mockDeck, deckBuilder);

        Deck resultDeck = deckBuilder.initializeDeck(MAX_PLAYERS);

        assertSame(mockDeck, resultDeck);
        List<Card> finalAssembledCards = capturedList.getValue();

        int expectedDeckSizeWithoutDefuses = getExpectedDeckSizeWithoutDefuses();
        int expectedDeckSizeAddDefuses = expectedDeckSizeWithoutDefuses
                + (NUM_DEFUSES_IN_GAME - MAX_PLAYERS);
        assertEquals(expectedDeckSizeAddDefuses, finalAssembledCards.size());

        verifyCardTypeGroup(
                finalAssembledCards,
                CardType.DEFUSE,
                NUM_DEFUSES_IN_GAME - MAX_PLAYERS,
                "DEFUSE");

        verify(mockDeck, deckBuilder);
    }

    @Test
    public void initializeDeckWithoutDefuses_TotalCardCount_EqualsBaselineConstant() {
        int expectedDeckSizeWithoutDefuses = getExpectedDeckSizeWithoutDefuses();
        assertEquals(expectedDeckSizeWithoutDefuses, baseCards.size());
    }

    @Test
    public void initializeDeckWithoutDefuses_SingleInstanceCards_PopulateCorrectCards() {
        verifyCardTypeGroup(baseCards, CardType.MILD_SHUFFLE,
                NUM_MILD_SHUFFLE_IN_GAME, "MILDSHUFFLE");
        verifyCardTypeGroup(baseCards, CardType.GODCAT, NUM_GODCAT_IN_GAME, "GODCAT");
        verifyCardTypeGroup(baseCards, CardType.WINNER_WINNER_CATNIP_DINNER,
                NUM_WINNER_WINNER_CATNIP_DINNER_IN_GAME, "WINNERWINNERCATNIPDINNER");
        verifyCardTypeGroup(baseCards, CardType.RAGEBAIT, NUM_RAGEBAIT_IN_GAME, "RAGEBAIT");
        verifyCardTypeGroup(baseCards, CardType.RECYCLE, NUM_RECYCLE_IN_GAME, "RECYCLE");
        verifyCardTypeGroup(baseCards, CardType.DOUBLE_UP, NUM_DOUBLE_UP_IN_GAME, "DOUBLEUP");
        verifyCardTypeGroup(baseCards, CardType.CATOMIC_BOMB,
                NUM_CATOMIC_BOMB_IN_GAME, "CATOMICBOMB");
    }

    @Test
    public void initializeDeckWithoutDefuses_DoubleInstanceCards_PopulateCorrectCards() {
        verifyCardTypeGroup(baseCards, CardType.SUPER_SKIP, NUM_SUPER_SKIP_IN_GAME, "SUPERSKIP");
    }

    @Test
    public void initializeDeckWithoutDefuses_TripleInstanceCards_PopulateCorrectCards() {
        verifyCardTypeGroup(baseCards, CardType.ATTACK, NUM_ATTACK_IN_GAME, "ATTACK");
        verifyCardTypeGroup(baseCards, CardType.SKIP, NUM_SKIP_IN_GAME, "SKIP");
        verifyCardTypeGroup(baseCards, CardType.CLONE, NUM_CLONE_IN_GAME, "CLONE");
        verifyCardTypeGroup(baseCards, CardType.SWAP_TOP_AND_BOTTOM,
                NUM_SWAP_TOP_AND_BOTTOM_IN_GAME, "SWAPTOPANDBOTTOM");
        verifyCardTypeGroup(baseCards, CardType.DRAW_FROM_THE_BOTTOM,
                NUM_DRAW_FROM_THE_BOTTOM_IN_GAME, "DRAWFROMTHEBOTTOM");
    }

    @Test
    public void initializeDeckWithoutDefuses_QuadrupleInstanceCards_PopulateCorrectCards() {
        verifyCardTypeGroup(baseCards, CardType.FERAL_CAT, NUM_FERAL_CAT_IN_GAME, "FERALCAT");
        verifyCardTypeGroup(baseCards, CardType.SEE_THE_FUTURE,
                NUM_SEE_THE_FUTURE_IN_GAME, "SEETHEFUTURE");
        verifyCardTypeGroup(baseCards, CardType.SHUFFLE, NUM_SHUFFLE_IN_GAME, "SHUFFLE");
        verifyCardTypeGroup(baseCards, CardType.TARGETED_ATTACK,
                NUM_TARGETED_ATTACK_IN_GAME, "TARGETEDATTACK");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_1,
                NUM_CAT_CARD_PER_TYPE_IN_GAME, "CATCARD1");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_2,
                NUM_CAT_CARD_PER_TYPE_IN_GAME, "CATCARD2");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_3,
                NUM_CAT_CARD_PER_TYPE_IN_GAME, "CATCARD3");
        verifyCardTypeGroup(baseCards, CardType.CAT_CARD_4,
                NUM_CAT_CARD_PER_TYPE_IN_GAME, "CATCARD4");
    }

    @Test
    public void calculateDefusesToAdd_NegativeDefuses_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                DeckBuilder.calculateDefusesToAdd(NUM_DEFUSES_IN_GAME + 1));
    }

    @Test
    public void calculateDefusesToAdd_MinimumPlayers_ReturnsThree() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MIN_PLAYERS);
        assertEquals(NUM_DEFUSES_IN_GAME - MIN_PLAYERS, numDefuses);
    }

    @Test
    public void calculateDefusesToAdd_MaximumPlayers_ReturnsOne() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MAX_PLAYERS);
        assertEquals(NUM_DEFUSES_IN_GAME - MAX_PLAYERS, numDefuses);
    }

    @Test
    public void calculateDefusesToAdd_OneOverMaximumPlayers_ReturnsZero() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(MAX_PLAYERS + 1);
        assertEquals(0, numDefuses);
    }

    @Test
    public void createCardID_LowerValidInput_ReturnsCorrectString() {
        String actualID = DeckBuilder.createCardId(CardType.FERAL_CAT, 1);
        assertEquals("FERALCAT_1", actualID);
    }

    @Test
    public void createCardID_UpperValidInput_ReturnsCorrectString() {
        String actualID = DeckBuilder.createCardId(CardType.ATTACK, NUM_ATTACK_IN_GAME);
        assertEquals("ATTACK_3", actualID);
    }

    @Test
    public void createCardId_ZeroSequenceNumber_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                DeckBuilder.createCardId(CardType.MILD_SHUFFLE, 0));
    }

    private void verifyCardTypeGroup(List<Card> cards, CardType type,
                                     int expectedQuantity, String idPrefix) {
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

    private static int getExpectedDeckSizeWithoutDefuses() {
        return NUM_MILD_SHUFFLE_IN_GAME
                + NUM_GODCAT_IN_GAME
                + NUM_WINNER_WINNER_CATNIP_DINNER_IN_GAME
                + NUM_RAGEBAIT_IN_GAME
                + NUM_RECYCLE_IN_GAME
                + NUM_DOUBLE_UP_IN_GAME
                + NUM_CATOMIC_BOMB_IN_GAME
                + NUM_SUPER_SKIP_IN_GAME
                + NUM_ATTACK_IN_GAME
                + NUM_SKIP_IN_GAME
                + NUM_CLONE_IN_GAME
                + NUM_SWAP_TOP_AND_BOTTOM_IN_GAME
                + NUM_DRAW_FROM_THE_BOTTOM_IN_GAME
                + NUM_FERAL_CAT_IN_GAME
                + NUM_SEE_THE_FUTURE_IN_GAME
                + NUM_SHUFFLE_IN_GAME
                + NUM_TARGETED_ATTACK_IN_GAME
                + (NUM_CAT_CARD_PER_TYPE_IN_GAME * NUM_CAT_CARD_TYPES_IN_GAME);
    }
}