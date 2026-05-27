package domain;

public class Card {
    private final CardType type;

    public Card(CardType type) {
        this.type = type;
    }

    public String getId() {
        return this.cardId;
    }

    public CardType getType() {
        return this.type;
    }
}
