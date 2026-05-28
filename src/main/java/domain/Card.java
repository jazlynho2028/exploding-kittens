package domain;

public class Card {
    private final String id;
    private final CardType type;
    private boolean isSelected;

    public Card(String id, CardType type) {
        this.id = id;
        this.type = type;
        this.isSelected = false;
    }

    public String getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void toggleSelected() {
        this.isSelected = !this.isSelected;
    }


}
