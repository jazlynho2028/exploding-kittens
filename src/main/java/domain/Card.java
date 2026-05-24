package domain;

public class Card {
    private final String id;
    private final CardType type;

    public Card(String id, CardType type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

}
