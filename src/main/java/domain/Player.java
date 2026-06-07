package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    private final String name;
    private final List<Card> hand;
    private boolean isAlive;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.isAlive = true;
    }

    int getHandSize() {
        return this.hand.size();
    }

    public List<Card> getHand() {
        return List.copyOf(hand);
    }

    public String getName() {
        return this.name;
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void toggleSelectedHandCardAt(int handCardIndex) {
        if (handCardIndex < 0 || handCardIndex >= this.hand.size()) {
            throw new IllegalArgumentException("error.invalidHandCardIndex");
        }
        hand.get(handCardIndex).toggleSelected();
    }

    public void removeCardFromHand(Card card) {
        if (!this.hand.contains(card)) {
            throw new IllegalStateException("error.cardNotInHand");
        }
        hand.remove(card);
    }

    public void deselectHandCards() {
        for (Card card : hand) {
            if (card.getIsSelected()) {
                card.toggleSelected();
            }
        }
    }

    public List<Card> getSelectedCards() {
        return hand.stream()
                .filter(Card::getIsSelected)
                .collect(Collectors.toList());
    }

    public List<String> getHandIds() {
        return hand.stream()
                .map(Card::getId)
                .collect(Collectors.toList());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void eliminate() {
        isAlive = false;
    }

}
