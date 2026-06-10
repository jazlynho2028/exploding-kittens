package domain;

import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;

import org.easymock.EasyMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
        assertEquals(0, player.getWinnerWinnerActivatedRound());

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
        assertEquals(0, player.getWinnerWinnerActivatedRound());

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
        assertEquals(0, player.getWinnerWinnerActivatedRound());

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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.toggleSelectedHandCardAt(-1)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
    }

    @Test
    public void toggleSelectedHandCardAt_validLowerBoundIndex_cardToggled() {
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.toggleSelectedHandCardAt(cardIndex)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
        EasyMock.verify(mockCard1, mockCard2);
    }

    @Test
    public void toggleSelectedHandCardAt_indexExactlyEqualToHandSize_throwsException() {
        Player player = new Player("Alice");
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);

        EasyMock.replay(mockCard1, mockCard2);
        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.toggleSelectedHandCardAt(2)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
        EasyMock.verify(mockCard1, mockCard2);
    }

    @Test
    public void toggleSelectedHandCardAt_emptyHandIndexZero_throwsException() {
        Player player = new Player("Alice");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.toggleSelectedHandCardAt(0)
        );

        assertEquals("error.invalidHandCardIndex", exception.getMessage());
    }

    @Test
    public void getName_validName_returnsExactString() {
        final String expectedName = "Alice";
        Player player = new Player(expectedName);

        String actualName = player.getName();

        assertEquals(expectedName, actualName);
    }

    @Test
    public void getSelectedCards_emptyHand_returnsEmptyList() {
        final int expectedSelectedCards = 0;
        Player player = new Player("Alice");

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());
    }

    @Test
    public void getSelectedCards_oneCardUnselected_returnsEmptyList() {
        final int expectedSelectedCards = 0;
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getIsSelected()).andReturn(false);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard);

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());

        EasyMock.verify(mockCard);
    }

    @Test
    public void getSelectedCards_oneCardSelected_returnsListWithCard() {
        final int expectedSelectedCards = 1;
        final int selectedCardIndex = 0;
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getIsSelected()).andReturn(true);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard);

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());
        assertSame(mockCard, selectedCards.get(selectedCardIndex));

        EasyMock.verify(mockCard);
    }

    @Test
    public void getSelectedCards_multipleCardsNoneSelected_returnsEmptyList() {
        final int expectedSelectedCards = 0;
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockCard3 = EasyMock.createMock(Card.class);

        EasyMock.expect(mockCard1.getIsSelected()).andReturn(false);
        EasyMock.expect(mockCard2.getIsSelected()).andReturn(false);
        EasyMock.expect(mockCard3.getIsSelected()).andReturn(false);
        EasyMock.replay(mockCard1, mockCard2, mockCard3);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);
        player.addCardToHand(mockCard3);

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());

        EasyMock.verify(mockCard1, mockCard2, mockCard3);
    }

    @Test
    public void getSelectedCards_multipleCardsSomeSelected_returnsOnlySelectedCards() {
        final int expectedSelectedCards = 2;
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockCard3 = EasyMock.createMock(Card.class);

        EasyMock.expect(mockCard1.getIsSelected()).andReturn(true);
        EasyMock.expect(mockCard2.getIsSelected()).andReturn(true);
        EasyMock.expect(mockCard3.getIsSelected()).andReturn(false);
        EasyMock.replay(mockCard1, mockCard2, mockCard3);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);
        player.addCardToHand(mockCard3);

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());
        assertTrue(selectedCards.contains(mockCard1));
        assertTrue(selectedCards.contains(mockCard2));
        assertFalse(selectedCards.contains(mockCard3));

        EasyMock.verify(mockCard1, mockCard2, mockCard3);
    }

    @Test
    public void getSelectedCards_multipleCardsAllSelected_returnsAllCards() {
        final int expectedSelectedCards = 3;
        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockCard3 = EasyMock.createMock(Card.class);

        EasyMock.expect(mockCard1.getIsSelected()).andReturn(true);
        EasyMock.expect(mockCard2.getIsSelected()).andReturn(true);
        EasyMock.expect(mockCard3.getIsSelected()).andReturn(true);
        EasyMock.replay(mockCard1, mockCard2, mockCard3);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);
        player.addCardToHand(mockCard3);

        List<Card> selectedCards = player.getSelectedCards();

        assertEquals(expectedSelectedCards, selectedCards.size());
        assertTrue(selectedCards.contains(mockCard1));
        assertTrue(selectedCards.contains(mockCard2));
        assertTrue(selectedCards.contains(mockCard3));

        EasyMock.verify(mockCard1, mockCard2, mockCard3);
    }

    @Test
    public void getHandIds_emptyHand_returnsEmptyList() {
        Player player = new Player("Alice");

        List<String> handIds = player.getHandIds();

        assertEquals(0, handIds.size());
    }

    @Test
    public void getHandIds_oneCard_returnsListWithOneId() {
        final String cardId = "DEFUSE_1";
        final int handSize = 1;
        final int cardIndex1 = 0;
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getId()).andReturn(cardId);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard);

        List<String> handIds = player.getHandIds();

        assertEquals(handSize, handIds.size());
        assertEquals(cardId, handIds.get(cardIndex1));

        EasyMock.verify(mockCard);
    }

    @Test
    public void getHandIds_multipleCards_returnsAllIdsInOrder() {
        final String cardId1 = "DEFUSE_1";
        final String cardId2 = "ATTACK_1";
        final String cardId3 = "SKIP_1";
        final int handSize = 3;
        final int cardIndex1 = 0;
        final int cardIndex2 = 1;
        final int cardIndex3 = 2;

        Card mockCard1 = EasyMock.createMock(Card.class);
        Card mockCard2 = EasyMock.createMock(Card.class);
        Card mockCard3 = EasyMock.createMock(Card.class);

        EasyMock.expect(mockCard1.getId()).andReturn(cardId1);
        EasyMock.expect(mockCard2.getId()).andReturn(cardId2);
        EasyMock.expect(mockCard3.getId()).andReturn(cardId3);
        EasyMock.replay(mockCard1, mockCard2, mockCard3);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard1);
        player.addCardToHand(mockCard2);
        player.addCardToHand(mockCard3);

        List<String> handIds = player.getHandIds();

        assertEquals(handSize, handIds.size());
        assertEquals(cardId1, handIds.get(cardIndex1));
        assertEquals(cardId2, handIds.get(cardIndex2));
        assertEquals(cardId3, handIds.get(cardIndex3));

        EasyMock.verify(mockCard1, mockCard2, mockCard3);
    }

    @Test
    public void getHandIds_duplicateCards_returnsDuplicateIds() {
        final String cardId = "DEFUSE_1";
        final int duplicateCount = 2;
        final int handSize = 2;
        final int cardIndex1 = 0;
        final int cardIndex2 = 1;

        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.expect(mockCard.getId()).andReturn(cardId).times(duplicateCount);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardToHand(mockCard);
        player.addCardToHand(mockCard);

        List<String> handIds = player.getHandIds();

        assertEquals(handSize, handIds.size());
        assertEquals(cardId, handIds.get(cardIndex1));
        assertEquals(cardId, handIds.get(cardIndex2));

        EasyMock.verify(mockCard);
    }

    @Test
    public void isAlive_playerIsAlive_returnTrue() {
        Player player = new Player("Audrey");

        boolean isAlive = player.isAlive();
        assertTrue(isAlive);
    }

    @Test
    public void isAlive_playerIsDead_returnFalse() {
        Player player = new Player("Audrey");
        player.eliminate();

        boolean isAlive = player.isAlive();
        assertFalse(isAlive);
    }

    @Test
    public void eliminatePlayer_playerCreated_setIsAliveToFalse() {
        Player player = new Player("Audrey");
        player.eliminate();
        player.eliminate();

        boolean isAlive = player.isAlive();
        assertFalse(isAlive);
    }

    @Test
    public void activateWinnerWinnerFromRound_roundZero_failed() {
        Player player = new Player("Audrey");

        int round = 0;
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                player.activateWinnerWinnerFromRound(round));

        String expectedMsg = "error.invalidRound";
        String actualMsg = exception.getMessage();

        assertEquals(expectedMsg, actualMsg);
    }

    @ParameterizedTest
    @CsvSource({
            "1",
            "2"
    })
    public void activateWinnerWinnerFromRound_validRound_setWinnerWinnerActivatedRound(
            int expectedActivatedRound) {

        Player player = new Player("Audrey");

        player.activateWinnerWinnerFromRound(expectedActivatedRound);

        int actualActivatedRound = player.getWinnerWinnerActivatedRound();
        assertEquals(expectedActivatedRound, actualActivatedRound);
    }

    @Test
    public void isWinnerWinnerActivated_roundZero_returnFalse() {
        Player player = new Player("Audrey");
        assertFalse(player.isWinnerWinnerActivated());
    }

    @Test
    public void isWinnerWinnerActivated_roundOne_returnTrue() {
        Player player = new Player("Audrey");
        int round = 1;
        player.activateWinnerWinnerFromRound(round);

        assertTrue(player.isWinnerWinnerActivated());
    }

    @Test
    public void swapHandWith_bothPlayersHaveCards_handsSwapped() {
        Card card1 = new Card("SKIP_1", CardType.SKIP);
        Card card2 = new Card("ATTACK_1", CardType.ATTACK);

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        player1.addCardToHand(card1);
        player2.addCardToHand(card2);

        player1.swapHandWith(player2);

        assertEquals(List.of(card2), player1.getHand());
        assertEquals(List.of(card1), player2.getHand());
    }

    @Test
    public void swapHandWith_oneEmptyHand_handsSwapped() {
        Card card1 = new Card("SKIP_1", CardType.SKIP);

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");

        player1.addCardToHand(card1);

        player1.swapHandWith(player2);

        assertTrue(player1.getHand().isEmpty());
        assertEquals(List.of(card1), player2.getHand());
    }

}
