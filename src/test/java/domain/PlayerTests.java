package domain;

import org.junit.jupiter.api.Test;

import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    public void removeCardFromHand_emptyHand_IllegalStateException() {
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
    public void removeCardFromHand_oneCardAndMissingCard_IllegalStateException() {
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
    public void removeCardFromHand_oneCardAndMatchingCard_handBecomesEmpty() {
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
    public void removeCardFromHand_multipleCardsAndMissingCard_IllegalStateException() {
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
    public void removeCardFromHand_multipleCardsAndMatchingCard_cardRemoved() {
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
    public void removeCardFromHand_duplicateCardsAndMissingCard_IllegalStateException() {
        Card mockDuplicateCard = EasyMock.createMock(Card.class);
        Card mockNonExistingCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockDuplicateCard, mockNonExistingCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockDuplicateCard);
        player.addCardToHand(mockDuplicateCard);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            player.removeCardFromHand(mockNonExistingCard);
        });
        assertEquals("error.cardNotInHand", exception.getMessage());

        EasyMock.verify(mockDuplicateCard, mockNonExistingCard);
    }

    @Test
    public void removeCardFromHand_duplicateCard_oneInstanceRemoved() {
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

    @Test
    public void deselectHandCards_emptyHand_noChanges() {
        final int handSize = 0;
        Player player = new Player("Alice");

        player.deselectHandCards();

        assertEquals(handSize, player.getHandSize());
    }

    @ParameterizedTest
    @CsvSource({
            "3, 0",
            "3, 1",
            "3, 2",
            "3, 3",
            "1, 0",
            "1, 1",
    })
    public void deselectHandCards_nonEmptyHand_allUnselected(int totalCards, int numToSelect) {
        Player player = new Player("Alice");
        Card[] mockCards = new Card[totalCards];

        for (int i = 0; i < totalCards; i++) {
            mockCards[i] = EasyMock.createMock(Card.class);

            boolean initiallySelected = (i < numToSelect);

            if (initiallySelected) {
                EasyMock.expect(mockCards[i].getIsSelected()).andReturn(true);

                mockCards[i].toggleSelected();

                EasyMock.expect(mockCards[i].getIsSelected()).andStubReturn(false);
            }
            else {
                EasyMock.expect(mockCards[i].getIsSelected()).andStubReturn(false);
            }

            player.addCardToHand(mockCards[i]);
        }
        EasyMock.replay((Object[]) mockCards);

        player.deselectHandCards();

        for (Card card : player.getHand()) {
            assertFalse(card.getIsSelected());
        }

        EasyMock.verify((Object[]) mockCards);
    }

    @Test
    public void toggleSelectedHandCardAt_indexLessThanZero_callsException() {
        Player player = new Player("Bob");

        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> player.toggleSelectedHandCardAt(-1)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
    }

    @Test
    public void toggleSelectedHandCardA_validLowerBoundIndex_cardToggled() {
        final int cardIndex = 0;
        Player player = new Player("Alice");
        Card mockCard = EasyMock.createMock(Card.class);

        mockCard.toggleSelected();
        EasyMock.replay(mockCard);

        player.addCardToHand(mockCard);

        player.toggleSelectedHandCardAt(cardIndex);

        EasyMock.verify(mockCard);
    }

    @Test
    public void toggleSelectedHandCardAt_indexUpperBound_cardToggled() {
        final int cardIndex = 1;
        Player player = new Player("Alice");

        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);

        mockCard2.toggleSelected();

        EasyMock.replay(mockCard1, mockCard2);

        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);

        player.toggleSelectedHandCardAt(cardIndex);

        EasyMock.verify(mockCard1, mockCard2);
    }

    @Test
    public void toggleSelectedHandCardAt_indexGreaterThanHandSize_callsException() {
        final int cardIndex = 3;
        Player player = new Player("Alice");

        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);

        EasyMock.replay(mockCard1, mockCard2);

        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);

        IndexOutOfBoundsException exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> player.toggleSelectedHandCardAt(cardIndex)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
        EasyMock.verify(mockCard1, mockCard2);
    }

}
