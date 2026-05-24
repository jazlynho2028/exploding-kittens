package domain;

public final class Card {
    private final CardType type;

    public Card(CardType type) {
        if (type == null) {
            throw new IllegalArgumentException("Card type cannot be null.");
        }
        this.type = type;
    }

    public CardType getType() {
        return this.type;
    }
}
