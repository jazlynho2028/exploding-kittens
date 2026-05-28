package domain;

public class Card {
    private final CardType type;
    private final String cardId;
    private boolean isSelected;

    public Card(String cardId, CardType type) {
        this.type = type;
        this.cardId = cardId;
        this.isSelected = false;
    }

    public String getId() {
        return this.cardId;
    }

    public CardType getType() {
        return this.type;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void toggleSelected() {
        this.isSelected = !this.isSelected;
    }
}
