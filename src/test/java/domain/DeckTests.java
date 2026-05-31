package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTests {
    private static final String ERROR_EMPTY_DECK = "error.emptyDeck";
    private static final String ERROR_NOT_ENOUGH_CARDS = "error.notEnoughCards";

    private static final int ONE_CARD = 1;
    private static final int TWO_CARDS = 2;
    private static final int THREE_CARDS = 3;

    @Test
    public void shuffle_emptyDeck_deckStaysEmpty() {
        Deque<Card> cards = new ArrayDeque<>();
        Random mockRandom = EasyMock.createMock(Random.class);
        EasyMock.replay(mockRandom);

        runShuffleTest(cards, mockRandom, List.of());

        EasyMock.verify(mockRandom);
    }

    @Test
    public void shuffle_oneCardDeck_deckOrderUnchanged() {
        Card card1 = EasyMock.createMock(Card.class);
        Random mockRandom = EasyMock.createMock(Random.class);
        EasyMock.replay(card1, mockRandom);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        runShuffleTest(cards, mockRandom, List.of(card1));

        EasyMock.verify(card1, mockRandom);
    }

    @Test
    public void shuffle_multipleDifferentCards_controlledRandomReordersDeck() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockRandom.nextInt(2)).andReturn(0);

        EasyMock.replay(card1, card2, mockRandom);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card2);
        cards.addLast(card1);

        runShuffleTest(cards, mockRandom, List.of(card1, card2));

        EasyMock.verify(card1, card2, mockRandom);
    }

    @Test
    public void shuffle_multipleDuplicateCards_controlledRandomReordersDeck() {
        Card card1CopyOne = EasyMock.createMock(Card.class);
        Card card1CopyTwo = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        Random mockRandom = EasyMock.createMock(Random.class);

        EasyMock.expect(mockRandom.nextInt(THREE_CARDS)).andReturn(0);
        EasyMock.expect(mockRandom.nextInt(TWO_CARDS)).andReturn(0);

        EasyMock.replay(card1CopyOne, card1CopyTwo, card2, mockRandom);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1CopyOne);
        cards.addLast(card1CopyTwo);
        cards.addLast(card2);

        runShuffleTest(
                cards,
                mockRandom,
                List.of(card1CopyTwo, card2, card1CopyOne));

        EasyMock.verify(card1CopyOne, card1CopyTwo, card2, mockRandom);
    }

    private void runShuffleTest(
            Deque<Card> originalCards,
            Random mockRandom,
            List<Card> expectedCards) {
        Deck deck = new Deck(originalCards, mockRandom);

        deck.shuffle();

        assertEquals(expectedCards.size(), deck.size());

        List<Card> actualCards = new ArrayList<>();
        while (deck.size() > 0) {
            actualCards.add(deck.removeTop());
        }

        assertEquals(expectedCards, actualCards);
    }

    @Test
    public void peekTop_emptyDeck_throwsIllegalStateException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::peekTop);

        assertEquals(ERROR_EMPTY_DECK, exception.getMessage());
        assertEquals(0, deck.size());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("peekTopNonEmptyDeckCases")
    public void peekTop_nonEmptyDeck_returnsTopCard(
            String caseName,
            Deque<Card> cards,
            Card expectedTop,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekTop();

        assertSame(expectedTop, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> peekTopNonEmptyDeckCases() {
        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCard = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard);
        Deque<Card> duplicateCardsDeck = new ArrayDeque<>();
        duplicateCardsDeck.addLast(duplicateCard);
        duplicateCardsDeck.addLast(duplicateCard);

        return Stream.of(
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        oneCard,
                        List.of(oneCard),
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        firstDifferentCard,
                        List.of(firstDifferentCard, secondDifferentCard),
                        new Card[] {firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        duplicateCard,
                        List.of(duplicateCard, duplicateCard),
                        new Card[] {duplicateCard})
        );
    }

    @Test
    public void removeTop_emptyDeck_throwsIllegalStateException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::removeTop);

        assertEquals(ERROR_EMPTY_DECK, exception.getMessage());
        assertEquals(0, deck.size());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("removeTopNonEmptyDeckCases")
    public void removeTop_nonEmptyDeck_returnsTopCard(
            String caseName,
            Deque<Card> cards,
            Card expectedRemovedCard,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeTop();

        assertSame(expectedRemovedCard, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> removeTopNonEmptyDeckCases() {
        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCard = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard);
        Deque<Card> duplicateCardsDeck = new ArrayDeque<>();
        duplicateCardsDeck.addLast(duplicateCard);
        duplicateCardsDeck.addLast(duplicateCard);

        return Stream.of(
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        oneCard,
                        List.of(),
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        firstDifferentCard,
                        List.of(secondDifferentCard),
                        new Card[] {firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        duplicateCard,
                        List.of(duplicateCard),
                        new Card[] {duplicateCard})
        );
    }

    @Test
    public void peekBottom_emptyDeck_throwsIllegalStateException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::peekBottom);

        assertEquals(ERROR_EMPTY_DECK, exception.getMessage());
        assertEquals(0, deck.size());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("peekBottomNonEmptyDeckCases")
    public void peekBottom_nonEmptyDeck_returnsBottomCard(
            String caseName,
            Deque<Card> cards,
            Card expectedBottom,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekBottom();

        assertSame(expectedBottom, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> peekBottomNonEmptyDeckCases() {
        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCard = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard);
        Deque<Card> duplicateCardsDeck = new ArrayDeque<>();
        duplicateCardsDeck.addLast(duplicateCard);
        duplicateCardsDeck.addLast(duplicateCard);

        return Stream.of(
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        oneCard,
                        List.of(oneCard),
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        secondDifferentCard,
                        List.of(firstDifferentCard, secondDifferentCard),
                        new Card[] {firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        duplicateCard,
                        List.of(duplicateCard, duplicateCard),
                        new Card[] {duplicateCard})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("peekTopNCardsInvalidCountCases")
    public void peekTopNCards_invalidCount_throwsException(
            String caseName,
            Deque<Card> cards,
            int n,
            Class<? extends RuntimeException> expectedExceptionType,
            String expectedMessage,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        RuntimeException exception = assertThrows(
                expectedExceptionType,
                () -> deck.peekTopNCards(n));

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> peekTopNCardsInvalidCountCases() {
        Deque<Card> emptyDeck = new ArrayDeque<>();

        Card negativeCountCard1 = EasyMock.createMock(Card.class);
        Card negativeCountCard2 = EasyMock.createMock(Card.class);
        EasyMock.replay(negativeCountCard1, negativeCountCard2);
        Deque<Card> negativeCountDeck = new ArrayDeque<>();
        negativeCountDeck.addLast(negativeCountCard1);
        negativeCountDeck.addLast(negativeCountCard2);

        Card tooLargeCountCard1 = EasyMock.createMock(Card.class);
        Card tooLargeCountCard2 = EasyMock.createMock(Card.class);
        EasyMock.replay(tooLargeCountCard1, tooLargeCountCard2);
        Deque<Card> tooLargeCountDeck = new ArrayDeque<>();
        tooLargeCountDeck.addLast(tooLargeCountCard1);
        tooLargeCountDeck.addLast(tooLargeCountCard2);

        return Stream.of(
                Arguments.of(
                        "empty deck and positive count",
                        emptyDeck,
                        ONE_CARD,
                        IllegalStateException.class,
                        ERROR_NOT_ENOUGH_CARDS,
                        List.of(),
                        new Card[] {}),
                Arguments.of(
                        "negative count",
                        negativeCountDeck,
                        -1,
                        IllegalArgumentException.class,
                        "Cannot peek a negative number of cards.",
                        List.of(negativeCountCard1, negativeCountCard2),
                        new Card[] {negativeCountCard1, negativeCountCard2}),
                Arguments.of(
                        "count greater than deck size",
                        tooLargeCountDeck,
                        THREE_CARDS,
                        IllegalStateException.class,
                        ERROR_NOT_ENOUGH_CARDS,
                        List.of(tooLargeCountCard1, tooLargeCountCard2),
                        new Card[] {tooLargeCountCard1, tooLargeCountCard2})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("peekTopNCardsZeroCountCases")
    public void peekTopNCards_zeroCount_returnsEmptyList(
            String caseName,
            Deque<Card> cards,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(0);

        assertEquals(List.of(), result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> peekTopNCardsZeroCountCases() {
        Deque<Card> emptyDeck = new ArrayDeque<>();

        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);
        Deque<Card> nonEmptyDeck = new ArrayDeque<>();
        nonEmptyDeck.addLast(card1);
        nonEmptyDeck.addLast(card2);

        return Stream.of(
                Arguments.of(
                        "empty deck",
                        emptyDeck,
                        List.of(),
                        new Card[] {}),
                Arguments.of(
                        "non-empty deck",
                        nonEmptyDeck,
                        List.of(card1, card2),
                        new Card[] {card1, card2})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("peekTopNCardsValidCountCases")
    public void peekTopNCards_validCount_returnsTopCardsInOrder(
            String caseName,
            Deque<Card> cards,
            int n,
            List<Card> expectedResult,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(n);

        assertEquals(expectedResult, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> peekTopNCardsValidCountCases() {
        Card oneCountCard1 = EasyMock.createMock(Card.class);
        Card oneCountCard2 = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCountCard1, oneCountCard2);
        Deque<Card> oneCountDeck = new ArrayDeque<>();
        oneCountDeck.addLast(oneCountCard1);
        oneCountDeck.addLast(oneCountCard2);

        Card fullDeckCard1 = EasyMock.createMock(Card.class);
        Card fullDeckCard2 = EasyMock.createMock(Card.class);
        EasyMock.replay(fullDeckCard1, fullDeckCard2);
        Deque<Card> fullDeck = new ArrayDeque<>();
        fullDeck.addLast(fullDeckCard1);
        fullDeck.addLast(fullDeckCard2);

        Card duplicateCard1 = EasyMock.createMock(Card.class);
        Card duplicateCard2 = EasyMock.createMock(Card.class);
        Card duplicateCard3 = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard1, duplicateCard2, duplicateCard3);
        Deque<Card> duplicateDeck = new ArrayDeque<>();
        duplicateDeck.addLast(duplicateCard1);
        duplicateDeck.addLast(duplicateCard2);
        duplicateDeck.addLast(duplicateCard3);

        return Stream.of(
                Arguments.of(
                        "one-card count",
                        oneCountDeck,
                        ONE_CARD,
                        List.of(oneCountCard1),
                        List.of(oneCountCard1, oneCountCard2),
                        new Card[] {oneCountCard1, oneCountCard2}),
                Arguments.of(
                        "count equals deck size",
                        fullDeck,
                        TWO_CARDS,
                        List.of(fullDeckCard1, fullDeckCard2),
                        List.of(fullDeckCard1, fullDeckCard2),
                        new Card[] {fullDeckCard1, fullDeckCard2}),
                Arguments.of(
                        "duplicate cards",
                        duplicateDeck,
                        TWO_CARDS,
                        List.of(duplicateCard1, duplicateCard2),
                        List.of(duplicateCard1, duplicateCard2, duplicateCard3),
                        new Card[] {duplicateCard1, duplicateCard2, duplicateCard3})
        );
    }

    @Test
    public void removeBottom_emptyDeck_throwsIllegalStateException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                deck::removeBottom);

        assertEquals(ERROR_EMPTY_DECK, exception.getMessage());
        assertEquals(0, deck.size());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("removeBottomNonEmptyDeckCases")
    public void removeBottom_nonEmptyDeck_returnsBottomCard(
            String caseName,
            Deque<Card> cards,
            Card expectedRemovedCard,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeBottom();

        assertSame(expectedRemovedCard, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> removeBottomNonEmptyDeckCases() {
        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCard = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard);
        Deque<Card> duplicateCardsDeck = new ArrayDeque<>();
        duplicateCardsDeck.addLast(duplicateCard);
        duplicateCardsDeck.addLast(duplicateCard);

        return Stream.of(
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        oneCard,
                        List.of(),
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        secondDifferentCard,
                        List.of(firstDifferentCard),
                        new Card[] {firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        duplicateCard,
                        List.of(duplicateCard),
                        new Card[] {duplicateCard})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("addCardCases")
    public void addCard_validCard_addsCardToBottom(
            String caseName,
            Deque<Card> cards,
            Card cardToAdd,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        deck.addCard(cardToAdd);

        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> addCardCases() {
        Card emptyDeckCard = EasyMock.createMock(Card.class);
        EasyMock.replay(emptyDeckCard);
        Deque<Card> emptyDeck = new ArrayDeque<>();

        Card oneCardDeckCard = EasyMock.createMock(Card.class);
        Card cardAddedToOneCardDeck = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCardDeckCard, cardAddedToOneCardDeck);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCardDeckCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        Card cardAddedToDifferentCards = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard, cardAddedToDifferentCards);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCard = EasyMock.createMock(Card.class);
        Card otherCard = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCard, otherCard);
        Deque<Card> duplicateCardDeck = new ArrayDeque<>();
        duplicateCardDeck.addLast(duplicateCard);
        duplicateCardDeck.addLast(otherCard);

        return Stream.of(
                Arguments.of(
                        "empty deck",
                        emptyDeck,
                        emptyDeckCard,
                        List.of(emptyDeckCard),
                        new Card[] {emptyDeckCard}),
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        cardAddedToOneCardDeck,
                        List.of(oneCardDeckCard, cardAddedToOneCardDeck),
                        new Card[] {oneCardDeckCard, cardAddedToOneCardDeck}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        cardAddedToDifferentCards,
                        List.of(firstDifferentCard, secondDifferentCard, cardAddedToDifferentCards),
                        new Card[] {
                                firstDifferentCard,
                                secondDifferentCard,
                                cardAddedToDifferentCards
                        }),
                Arguments.of(
                        "duplicate card",
                        duplicateCardDeck,
                        duplicateCard,
                        List.of(duplicateCard, otherCard, duplicateCard),
                        new Card[] {duplicateCard, otherCard})
        );
    }

    @Test
    public void isEmpty_emptyDeck_returnsTrue() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        assertTrue(deck.isEmpty());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("nonEmptyDeckCases")
    public void isEmpty_nonEmptyDeck_returnsFalse(
            String caseName,
            Deque<Card> cards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        assertFalse(deck.isEmpty());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> nonEmptyDeckCases() {
        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, secondCard);
        Deque<Card> multipleCardDeck = new ArrayDeque<>();
        multipleCardDeck.addLast(firstCard);
        multipleCardDeck.addLast(secondCard);

        return Stream.of(
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple-card deck",
                        multipleCardDeck,
                        new Card[] {firstCard, secondCard})
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getCardsCases")
    public void getCards_called_returnsCopyInDeckOrder(
            String caseName,
            Deque<Card> cards,
            List<Card> expectedCards,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.getCards();

        assertEquals(expectedCards, result);
        assertEquals(expectedCards, deck.getCards());

        EasyMock.verify((Object[]) mocksToVerify);
    }

    private static Stream<Arguments> getCardsCases() {
        Deque<Card> emptyDeck = new ArrayDeque<>();

        Card oneCard = EasyMock.createMock(Card.class);
        EasyMock.replay(oneCard);
        Deque<Card> oneCardDeck = new ArrayDeque<>();
        oneCardDeck.addLast(oneCard);

        Card firstDifferentCard = EasyMock.createMock(Card.class);
        Card secondDifferentCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstDifferentCard, secondDifferentCard);
        Deque<Card> differentCardsDeck = new ArrayDeque<>();
        differentCardsDeck.addLast(firstDifferentCard);
        differentCardsDeck.addLast(secondDifferentCard);

        Card duplicateCardOne = EasyMock.createMock(Card.class);
        Card duplicateCardTwo = EasyMock.createMock(Card.class);
        Card cardAfterDuplicates = EasyMock.createMock(Card.class);
        EasyMock.replay(duplicateCardOne, duplicateCardTwo, cardAfterDuplicates);
        Deque<Card> duplicateCardsDeck = new ArrayDeque<>();
        duplicateCardsDeck.addLast(duplicateCardOne);
        duplicateCardsDeck.addLast(duplicateCardTwo);
        duplicateCardsDeck.addLast(cardAfterDuplicates);

        return Stream.of(
                Arguments.of(
                        "empty deck",
                        emptyDeck,
                        List.of(),
                        new Card[]{}),
                Arguments.of(
                        "one-card deck",
                        oneCardDeck,
                        List.of(oneCard),
                        new Card[]{oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        List.of(firstDifferentCard, secondDifferentCard),
                        new Card[]{firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        List.of(duplicateCardOne, duplicateCardTwo, cardAfterDuplicates),
                        new Card[]{
                                duplicateCardOne,
                                duplicateCardTwo,
                                cardAfterDuplicates
                        })
        );
    }
}