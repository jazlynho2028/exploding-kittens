package domain;

public class DeckBuilder {

    public static String createCardId(CardType cardType, int id) {
        return cardType.toString().toLowerCase() + "-" + id;
    }
}
