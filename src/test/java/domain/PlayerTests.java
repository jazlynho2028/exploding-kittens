package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import org.easymock.EasyMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTests {

    @Test
    public void TestAddCardToHand_WhenHandIsEmpty() {
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice");
        player.addCardtoHand(mockCard);

        assertEquals(1, player.getHand().size());
        assertEquals(mockCard, player.getHand().get(0));

        EasyMock.verify(mockCard);
    }

    @Test
    public void TestAddCardToHand_WhenHandHasOneCard() {
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
    public void TestAddCardToHand_WhenHandHasFiveCards() {
        Player player = new Player("Alice");
        for (int i = 0; i < 5; i++) {
            Card initialCard = EasyMock.createMock(Card.class);
            EasyMock.replay(initialCard);
            player.addCardtoHand(initialCard);
        }

        Card sixthCard = EasyMock.createMock(Card.class);
        EasyMock.replay(sixthCard);

        player.addCardtoHand(sixthCard);

        assertEquals(6, player.getHand().size());
        assertEquals(sixthCard, player.getHand().get(5));

        EasyMock.verify(sixthCard);
    }

    @Test
    public void TestAddCardToHand_WithDuplicateCards() {
        Card mockDuplicateCard = EasyMock.createMock(Card.class);
        Card mockNewCard = EasyMock.createMock(Card.class);

        EasyMock.replay(mockDuplicateCard, mockNewCard);

        Player player = new Player("Alice");

        player.addCardtoHand(mockDuplicateCard);
        player.addCardtoHand(mockDuplicateCard);

        player.addCardtoHand(mockNewCard);

        assertEquals(3, player.getHand().size());
        assertEquals(mockDuplicateCard, player.getHand().get(0));
        assertEquals(mockDuplicateCard, player.getHand().get(1));
        assertEquals(mockNewCard, player.getHand().get(2));

        EasyMock.verify(mockDuplicateCard, mockNewCard);
    }

    @Test
    public void TestRemoveCardFromHand_NonExistingCardFromEmptyHand() {
        Player player = new Player("Alice");
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        assertThrows(IllegalArgumentException.class, () -> {
            player.removeCardFromHand(mockCard);
        });

        EasyMock.verify(mockCard);
    }
}
