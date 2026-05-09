package domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public int getHandSize() {
        return this.hand.size();
    }

    public void addCardtoHand(Card card) {
        this.hand.add(card);
    }

    public void removeCardFromHand(Card card) {
        if (!this.hand.contains(card)) {
            throw new IllegalArgumentException("Card not found in player hand.");
        }
        this.hand.remove(card);
    }
}
