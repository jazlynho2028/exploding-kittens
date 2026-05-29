package domain;

import org.junit.jupiter.api.Test;

import org.easymock.EasyMock;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {

    @Test
    public void addCardToHand_emptyHand_cardAddedToEnd() {
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard);

        assertEquals(1, player.getHand().size());
        assertEquals(mockCard, player.getHand().get(0));

        EasyMock.verify(mockCard);
    }

    @Test
    public void addCardToHand_handHasOneCard_cardAddedToEnd() {
        Card mockFirstCard = EasyMock.createMock(Card.class);
        Card mockSecondCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockFirstCard, mockSecondCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockFirstCard);
        player.addCardToHand(mockSecondCard);

        assertEquals(2, player.getHandSize());
        assertSame(mockFirstCard, player.getHand().get(0));
        assertEquals(mockSecondCard, player.getHand().get(1));

        EasyMock.verify(mockFirstCard, mockSecondCard);
    }

    @Test
    public void addCardToHand_handHasFiveCards_cardAddedToEnd() {
        final int finalHandSize = 6;
        final int finalCardIndex = 5;
        Player player = new Player("Alice");

        Card card1 = EasyMock.createMock(Card.class);
        Card card2 = EasyMock.createMock(Card.class);
        Card card3 = EasyMock.createMock(Card.class);
        Card card4 = EasyMock.createMock(Card.class);
        Card card5 = EasyMock.createMock(Card.class);

        EasyMock.replay(card1, card2, card3, card4, card5);

        player.addCardToHand(card1);
        player.addCardToHand(card2);
        player.addCardToHand(card3);
        player.addCardToHand(card4);
        player.addCardToHand(card5);

        Card card6 = EasyMock.createMock(Card.class);
        EasyMock.replay(card6);

        player.addCardToHand(card6);

        assertEquals(finalHandSize, player.getHand().size());
        assertEquals(card6, player.getHand().get(finalCardIndex));

        EasyMock.verify(card1, card2, card3, card4, card5, card6);
    }

    @Test
    public void addCardToHand_handHasDuplicateCards_cardAddedToEnd() {
        final int finalHandSize = 3;
        Card mockDuplicateCard = EasyMock.createMock(Card.class);
        Card mockNewCard = EasyMock.createMock(Card.class);

        EasyMock.replay(mockDuplicateCard, mockNewCard);

        Player player = new Player("Alice");

        player.addCardToHand(mockDuplicateCard);
        player.addCardToHand(mockDuplicateCard);

        player.addCardToHand(mockNewCard);

        assertEquals(finalHandSize, player.getHand().size());
        assertEquals(mockDuplicateCard, player.getHand().get(0));
        assertEquals(mockDuplicateCard, player.getHand().get(1));
        assertEquals(mockNewCard, player.getHand().get(2));

        EasyMock.verify(mockDuplicateCard, mockNewCard);
    }

    @Test
    public void removeCardFromHand_emptyHand_throwsGameException() {
        Player player = new Player("Alice");
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            player.removeCardFromHand(mockCard);
        });
        assertEquals("error.cardNotInHand", exception.getMessage());

        EasyMock.verify(mockCard);
    }

    @Test
    public void removeCardFromHand_oneCardAndMissingCard_throwsGameException() {
        Card mockExistingCard = EasyMock.createMock(Card.class);
        Card mockNonExistingCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockExistingCard, mockNonExistingCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockExistingCard);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            player.removeCardFromHand(mockNonExistingCard);
        });
        assertEquals("error.cardNotInHand", exception.getMessage());

        EasyMock.verify(mockExistingCard, mockNonExistingCard);
    }

    @Test
    public void removeCardFromHand_oneCardAndMatchingCard_handBecomesEmpty() throws GameException {
        Card mockExistingCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockExistingCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockExistingCard);

        player.removeCardFromHand(mockExistingCard);

        assertEquals(0, player.getHand().size());
        assertFalse(player.getHand().contains(mockExistingCard));

        EasyMock.verify(mockExistingCard);
    }

    @Test
    public void removeCardFromHand_multipleCardsAndMissingCard_throwsGameException() {
        Card mockFirstCard = EasyMock.createMock(Card.class);
        Card mockSecondCard = EasyMock.createMock(Card.class);
        Card mockNonExistingCard = EasyMock.createMock(Card.class);

        EasyMock.replay(mockFirstCard, mockSecondCard, mockNonExistingCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockFirstCard);
        player.addCardToHand(mockSecondCard);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            player.removeCardFromHand(mockNonExistingCard);
        });
        assertEquals("error.cardNotInHand", exception.getMessage());

        EasyMock.verify(mockFirstCard, mockSecondCard, mockNonExistingCard);
    }

    @Test
    public void removeCardFromHand_multipleCardsAndMatchingCard_cardRemoved() throws GameException {
        final int maintainHandSize = 1;
        Card mockCardToRemove = EasyMock.createMock(Card.class);
        Card mockCardToKeep = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCardToRemove, mockCardToKeep);

        Player player = new Player("Alice");
        player.addCardToHand(mockCardToRemove);
        player.addCardToHand(mockCardToKeep);

        player.removeCardFromHand(mockCardToRemove);

        assertEquals(maintainHandSize, player.getHand().size());
        assertFalse(player.getHand().contains(mockCardToRemove));
        assertSame(mockCardToKeep, player.getHand().get(0));

        EasyMock.verify(mockCardToRemove, mockCardToKeep);
    }

    @Test
    public void removeCardFromHand_duplicateCard_oneInstanceRemoved() throws GameException {
        final int expectedFinalSize = 1;
        Card mockDuplicateCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockDuplicateCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockDuplicateCard);
        player.addCardToHand(mockDuplicateCard);

        player.removeCardFromHand(mockDuplicateCard);

        assertEquals(expectedFinalSize, player.getHandSize());
        assertSame(mockDuplicateCard, player.getHand().get(0));

        EasyMock.verify(mockDuplicateCard);
    }
}
