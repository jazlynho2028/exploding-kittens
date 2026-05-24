package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTests {
    @Test
    public void toggleSelected_CurrentFalse_Success() {
        String id = "EXPLODING_KITTEN_1";
        CardType type = EasyMock.createMock(CardType.class);
        Card card = new Card(id, type);

        card.toggleSelected();

        assertTrue(card.getIsSelected());
    }
}
