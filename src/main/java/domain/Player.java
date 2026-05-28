package domain;

import java.util.List;

public class Player {

    public Player(String name) { }

    public List<Card> getHand() {
        return List.of();
    }

    public String getName() {
        return "";
    }

    public void addCardToHand(Card card) { }

    public void setIsSelectedOfHandCardToOpposite(int handCardIndex) { }

    public void removeCardFromHand(Card card) { }

}
