package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import org.easymock.EasyMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {

    @Test
    public void testAddCardToHand_WhenHandIsEmpty() {
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockCard);

        assertEquals(1, player.getHand().size());
        assertEquals(mockCard, player.getHand().get(0));

        EasyMock.verify(mockCard);
    }

    @Test
    public void testAddCardToHand_WhenHandHasOneCard() {
        Card mockFirstCard = EasyMock.createMock(Card.class);
        Card mockSecondCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockFirstCard, mockSecondCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockFirstCard);
        player.addCardtoHand(mockSecondCard);

        assertEquals(2, player.getHandSize());
        assertEquals(mockSecondCard, player.getHand().get(1));

        EasyMock.verify(mockFirstCard, mockSecondCard);
    }

    @Test
    public void testAddCardToHand_WhenHandHasFiveCards() {
        final int finalHandSize = 6;
        final int finalCardIndex = 5;
        Player player = new Player("Alice");

        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        Card card3 = EasyMock.createMock(Card.class);
        Card card4 = EasyMock.createMock(Card.class);
        Card card5 = EasyMock.createMock(Card.class);

        EasyMock.replay(card1, card2, card3, card4, card5);

        player.addCardtoHand(card1);
        player.addCardtoHand(card2);
        player.addCardtoHand(card3);
        player.addCardtoHand(card4);
        player.addCardtoHand(card5);

        Card card6 = EasyMock.createMock(Card.class);
        EasyMock.replay(card6);

        player.addCardtoHand(card6);

        assertEquals(finalHandSize, player.getHand().size());
        assertEquals(card6, player.getHand().get(finalCardIndex));

        EasyMock.verify(card1, card2, card3, card4, card5, card6);
    }

    @Test
    public void testAddCardToHand_WithDuplicateCards() {
        final int finalHandSize = 3;
        Card mockDuplicateCard = EasyMock.createMock(Card.class);
        Card mockNewCard = EasyMock.createMock(Card.class);

        EasyMock.replay(mockDuplicateCard, mockNewCard);

        Player player = new Player("Alice");

        player.addCardtoHand(mockDuplicateCard);
        player.addCardtoHand(mockDuplicateCard);

        player.addCardtoHand(mockNewCard);

        assertEquals(finalHandSize, player.getHand().size());
        assertEquals(mockDuplicateCard, player.getHand().get(0));
        assertEquals(mockDuplicateCard, player.getHand().get(1));
        assertEquals(mockNewCard, player.getHand().get(2));

        EasyMock.verify(mockDuplicateCard, mockNewCard);
    }

    @Test
    public void testRemoveCardFromHand_NonExistingCardFromEmptyHand() {
        Player player = new Player("Alice");
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeCardFromHand(mockCard);
        });

        EasyMock.verify(mockCard);
    }

    @Test
    public void testRemoveCardFromHand_NonExistingCardFromHandWithOneCard() {
        Card mockExistingCard = EasyMock.createMock(Card.class);
        Card mockNonexistingCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockExistingCard, mockNonexistingCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockExistingCard);

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeCardFromHand(mockNonexistingCard);
        });

        EasyMock.verify(mockExistingCard, mockNonexistingCard);
    }

    @Test
    public void testRemoveCardFromHand_ExistingCardFromHandWithOneCard() {
        Card mockExistingCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockExistingCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockExistingCard);

        player.removeCardFromHand(mockExistingCard);

        assertEquals(0, player.getHand().size());
        assertFalse(player.getHand().contains(mockExistingCard));

        EasyMock.verify(mockExistingCard);
    }

    @Test
    public void testRemoveCardFromHand_NonExistingCardFromCardWithMultipleCards() {
        Card mockFirstCard = EasyMock.createMock(Card.class);
        Card mockSecondCard = EasyMock.createMock(Card.class);
        Card mockNonexistingCard = EasyMock.createMock(Card.class);

        EasyMock.replay(mockFirstCard, mockSecondCard, mockNonexistingCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockFirstCard);
        player.addCardtoHand(mockSecondCard);

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeCardFromHand(mockNonexistingCard);
        });

        EasyMock.verify(mockFirstCard, mockSecondCard, mockNonexistingCard);
    }
}
