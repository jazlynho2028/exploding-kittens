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

    public void toggleSelectedHandCardAt(int index) { }

    public void removeCardFromHand(Card card) { }

    public void deselectHandCards() { }

    public List<String> getHandIds() {
        return List.of();
    }

    public List<Card> getSelectedCards() {
        return List.of();
    }

}
