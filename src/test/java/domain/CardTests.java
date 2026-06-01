package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardTests {
    @Test
    public void toggleSelected_CurrentFalse_Success() {
        String id = "EXPLODINGKITTEN_1";
        CardType type = CardType.CAT_CARD_1;
        Card card = new Card(id, type);

        card.toggleSelected();

        assertTrue(card.getIsSelected());
    }

    @Test
    public void toggleSelected_CurrentTrue_Success() {
        String id = "EXPLODINGKITTEN_1";
        CardType type = CardType.CAT_CARD_1;
        Card card = new Card(id, type);

        card.toggleSelected();
        card.toggleSelected();

        assertFalse(card.getIsSelected());
    }

    @Test
    public void setIsSelected_setToTrue() {
        String id = "EXPLODINGKITTEN_1";
        CardType type = CardType.CAT_CARD_1;
        Card card = new Card(id, type);

        card.setIsSelected(true);

        assertTrue(card.getIsSelected());
    }
}
