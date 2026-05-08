package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    @Test
    public void TestGetNameWhenNameIsFilled() {
        String expected = "Alice";
        Player player = new Player(expected);

        String actual = player.getName();

        assertEquals(expected, actual);
    }

}
