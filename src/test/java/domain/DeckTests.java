package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

        Deck deck = new Deck(cards, mockRandom);

        deck.shuffle();

        assertEquals(0, deck.size());

        EasyMock.verify(mockRandom);
    }

    @Test
    public void shuffle_oneCardDeck_deckOrderUnchanged() {
        Card card1 = EasyMock.createMock(Card.class);
        Random mockRandom = EasyMock.createMock(Random.class);
        EasyMock.replay(card1, mockRandom);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, mockRandom);

        deck.shuffle();

        assertEquals(1, deck.size());
        assertSame(card1, deck.peekTop());

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

        Deck deck = new Deck(cards, mockRandom);

        deck.shuffle();

        assertEquals(2, deck.size());
        assertSame(card1, deck.peekTop());

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

        Deck deck = new Deck(cards, mockRandom);

        deck.shuffle();

        assertEquals(THREE_CARDS, deck.size());
        assertSame(card1CopyTwo, deck.removeTop());
        assertSame(card2, deck.removeTop());
        assertSame(card1CopyOne, deck.removeTop());
        assertEquals(0, deck.size());

        EasyMock.verify(card1CopyOne, card1CopyTwo, card2, mockRandom);
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

    @Test
    public void peekTop_oneCardDeck_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void peekTop_multipleDifferentCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTop_multipleDuplicateCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekTop();

        assertSame(card1, result);
        assertEquals(2, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
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

    @Test
    public void removeTop_oneCardDeck_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(0, deck.size());

        EasyMock.verify(card1);
    }

    @Test
    public void removeTop_multipleDifferentCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card2, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void removeTop_multipleDuplicateCards_returnsTopCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeTop();

        assertSame(card1, result);
        assertEquals(1, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
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
            int expectedSize,
            Card expectedTop,
            Card[] mocksToVerify) {
        Deck deck = new Deck(cards, new Random());

        Card result = deck.peekBottom();

        assertSame(expectedBottom, result);
        assertEquals(expectedSize, deck.size());
        assertSame(expectedTop, deck.peekTop());
        assertSame(expectedBottom, deck.peekBottom());

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
                        ONE_CARD,
                        oneCard,
                        new Card[] {oneCard}),
                Arguments.of(
                        "multiple different cards",
                        differentCardsDeck,
                        secondDifferentCard,
                        TWO_CARDS,
                        firstDifferentCard,
                        new Card[] {firstDifferentCard, secondDifferentCard}),
                Arguments.of(
                        "multiple duplicate cards",
                        duplicateCardsDeck,
                        duplicateCard,
                        TWO_CARDS,
                        duplicateCard,
                        new Card[] {duplicateCard})
        );
    }

    @Test
    public void peekTopNCards_emptyDeckAndZeroCards_returnsEmptyList() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(0);

        assertEquals(0, result.size());
        assertEquals(0, deck.size());
    }

    @Test
    public void peekTopNCards_emptyDeckAndPositiveCount_throwsIllegalStateException() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> deck.peekTopNCards(ONE_CARD));

        assertEquals(ERROR_NOT_ENOUGH_CARDS, exception.getMessage());
        assertEquals(0, deck.size());
    }

    @Test
    public void peekTopNCards_negativeCount_throwsIllegalArgumentException() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        assertThrows(IllegalArgumentException.class, () -> deck.peekTopNCards(-1));
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTopNCards_zeroCount_returnsEmptyList() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(0);

        assertEquals(0, result.size());
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTopNCards_oneCardCount_returnsTopCardOnly() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(1);

        assertEquals(1, result.size());
        assertSame(card1, result.get(0));
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTopNCards_countEqualsDeckSize_returnsAllCards() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(TWO_CARDS);

        assertEquals(TWO_CARDS, result.size());
        assertSame(card1, result.get(0));
        assertSame(card2, result.get(1));
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTopNCards_countGreaterThanDeckSize_throwsIllegalStateException() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> deck.peekTopNCards(THREE_CARDS));

        assertEquals(ERROR_NOT_ENOUGH_CARDS, exception.getMessage());
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void peekTopNCards_duplicateCards_returnsTopCardsInOrder() {
        Card card1CopyOne = EasyMock.createMock(Card.class);
        Card card1CopyTwo = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1CopyOne, card1CopyTwo, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1CopyOne);
        cards.addLast(card1CopyTwo);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.peekTopNCards(TWO_CARDS);

        assertEquals(TWO_CARDS, result.size());
        assertSame(card1CopyOne, result.get(0));
        assertSame(card1CopyTwo, result.get(1));
        assertEquals(THREE_CARDS, deck.size());
        assertSame(card1CopyOne, deck.peekTop());

        EasyMock.verify(card1CopyOne, card1CopyTwo, card2);
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

    @Test
    public void removeBottom_oneCardDeck_returnsBottomCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeBottom();

        assertSame(card1, result);
        assertEquals(0, deck.size());

        EasyMock.verify(card1);
    }

    @Test
    public void removeBottom_multipleDifferentCards_returnsBottomCard() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeBottom();

        assertSame(card2, result);
        assertEquals(ONE_CARD, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void removeBottom_multipleDuplicateCards_returnsBottomCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        Card result = deck.removeBottom();

        assertSame(card1, result);
        assertEquals(ONE_CARD, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void addCard_emptyDeck_addsCardToDeck() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        deck.addCard(card1);

        assertEquals(ONE_CARD, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void addCard_oneCardDeck_addsCardToBottom() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        deck.addCard(card2);

        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());
        assertSame(card2, deck.peekBottom());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void addCard_multipleDifferentCards_addsCardToBottom() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        Card card3 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2, card3);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        deck.addCard(card3);

        assertEquals(THREE_CARDS, deck.size());
        assertSame(card1, deck.peekTop());
        assertSame(card3, deck.peekBottom());

        EasyMock.verify(card1, card2, card3);
    }

    @Test
    public void addCard_duplicateCard_addsCardToBottom() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        deck.addCard(card1);

        assertEquals(THREE_CARDS, deck.size());
        assertSame(card1, deck.peekTop());
        assertSame(card1, deck.peekBottom());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void isEmpty_emptyDeck_returnsTrue() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        assertTrue(deck.isEmpty());
    }

    @Test
    public void isEmpty_oneCardDeck_returnsFalse() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        assertFalse(deck.isEmpty());

        EasyMock.verify(card1);
    }

    @Test
    public void isEmpty_multipleCards_returnsFalse() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        assertFalse(deck.isEmpty());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void getCards_emptyDeck_returnsEmptyList() {
        Deque<Card> cards = new ArrayDeque<>();
        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.getCards();

        assertEquals(0, result.size());
        assertEquals(0, deck.size());
    }

    @Test
    public void getCards_oneCardDeck_returnsCopyWithOneCard() {
        Card card1 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.getCards();

        assertEquals(ONE_CARD, result.size());
        assertSame(card1, result.get(0));
        assertEquals(ONE_CARD, deck.size());
        assertSame(card1, deck.peekTop());

        EasyMock.verify(card1);
    }

    @Test
    public void getCards_multipleDifferentCards_returnsCopyInOrderWithoutDuplicates() {
        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.getCards();

        assertEquals(TWO_CARDS, result.size());
        assertSame(card1, result.get(0));
        assertSame(card2, result.get(1));
        assertEquals(TWO_CARDS, deck.size());
        assertSame(card1, deck.peekTop());
        assertSame(card2, deck.peekBottom());

        EasyMock.verify(card1, card2);
    }

    @Test
    public void getCards_multipleDuplicateCards_returnsCopyInOrderWithDuplicates() {
        Card card1CopyOne = EasyMock.createMock(Card.class);
        Card card1CopyTwo = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        EasyMock.replay(card1CopyOne, card1CopyTwo, card2);

        Deque<Card> cards = new ArrayDeque<>();
        cards.addLast(card1CopyOne);
        cards.addLast(card1CopyTwo);
        cards.addLast(card2);

        Deck deck = new Deck(cards, new Random());

        List<Card> result = deck.getCards();

        assertEquals(THREE_CARDS, result.size());
        assertSame(card1CopyOne, result.get(0));
        assertSame(card1CopyTwo, result.get(1));
        assertSame(card2, result.get(2));
        assertEquals(THREE_CARDS, deck.size());
        assertSame(card1CopyOne, deck.peekTop());
        assertSame(card2, deck.peekBottom());

        EasyMock.verify(card1CopyOne, card1CopyTwo, card2);
    }
}