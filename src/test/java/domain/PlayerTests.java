package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import org.easymock.EasyMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    @Test
    public void TestGetName_WhenNameIsFilledAndMinPlayers() {
        String expected = "Alice";
        int playerNum = 1;
        Player player = new Player(expected, playerNum);

        String actual = player.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void TestGetName_WhenNameIsFilledAndMaxPlayers() {
        String expected = "Alice";
        int playerNum = 4;
        Player player = new Player(expected, playerNum);

        String actual = player.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void TestGetName_WhenNameIsEmptyAndMinPlayers() {
        int playerNum = 1;
        Player player = new Player("", playerNum);

        String actual = player.getName();

        assertEquals("Player 1", actual);
    }

    @Test
    public void TestGetName_WhenNameIsEmptyAndMaxPlayers() {
        int playerNum = 4;
        Player player = new Player("", playerNum);

        String actual = player.getName();

        assertEquals("Player 4", actual);
    }

    @Test
    public void TestAddCardToHand_WhenHandIsEmpty() {
        Card mockCard = EasyMock.createMock(Card.class);
        EasyMock.replay(mockCard);

        Player player = new Player("Alice", 1);

        player.addCardtoHand(mockCard);

        assertEquals(1, player.getHand().size());
        assertEquals(mockCard, player.getHand().get(0));

        EasyMock.verify(mockCard);
    }
}
