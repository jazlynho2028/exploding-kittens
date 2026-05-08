package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    @Test
    public void TestGetNameWhenNameIsFilledAndMinPlayers() {
        String expected = "Alice";
        int playerNum = 1;
        Player player = new Player(expected, playerNum);

        String actual = player.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void TestGetNameWhenNameIsFilledAndMaxPlayers() {
        String expected = "Alice";
        int playerNum = 4;
        Player player = new Player(expected, playerNum);

        String actual = player.getName();

        assertEquals(expected, actual);
    }

    @Test
    public void TestGetNameWhenNameIsEmptyAndMinPlayers() {
        int playerNum = 1;
        Player player = new Player("", playerNum);

        String actual = player.getName();

        assertEquals("Player 1", actual);
    }

    @Test
    public void TestGetNameWhenNameIsEmptyAndMaxPlayers() {
        int playerNum = 4;
        Player player = new Player("", playerNum);

        String actual = player.getName();

        assertEquals("Player 4", actual);
    }
}
