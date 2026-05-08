package domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private List<Card> hand;

    public Player(String name, int playerNumber) {
        if (name == null || name.isEmpty()) {
            this.name = "Player " + playerNumber;
        }
        else {
            this.name = name;
        }
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void addCardtoHand() {

    }

    public void removeCardFromHand() {

    }
}
