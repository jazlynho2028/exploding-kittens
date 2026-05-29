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
        return List.copyOf(hand);
    }

    public int getHandSize() {
        return this.hand.size();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public void setIsSelectedOfHandCardToOpposite(int handCardIndex) {
        hand.get(handCardIndex).toggleSelected();
    }

    public void removeCardFromHand(Card card) throws GameException {
        if (!this.hand.contains(card)) {
            throw new GameException("error.cardNotInHand");
        }
        this.hand.remove(card);
    }
}
