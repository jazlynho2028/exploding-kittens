package domain;

import java.util.ArrayList;
import java.util.List;

public final class Player {
    private final String name;
    private final List<Card> hand;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty.");
        }
        this.name = name;
        this.hand = new ArrayList<>(); // Initialize the hand as an empty collection
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public String getName() {
        return this.name;
    }

    public void addCardToHand(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Cannot add a null card to hand.");
        }
        this.hand.add(card);
    }

}
