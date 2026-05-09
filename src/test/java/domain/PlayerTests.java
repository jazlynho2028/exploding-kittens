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
}
