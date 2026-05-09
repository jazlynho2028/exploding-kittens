package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import org.easymock.EasyMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
