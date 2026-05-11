package domain;

public class DeckBuilder {
    public static Deck buildDeckWithoutExplodeAndDefuse(int numPlayers) {
        if (numPlayers < 2 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players must be between 2 and 4");
        }
        return null;
    }
}
