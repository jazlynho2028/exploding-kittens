package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckBuilderTests {
    @Test
    void buildDeck_LessThanMinPlayers_ThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(1);
        });
    }

    @Test
    void buildDeck_MoreThanMaxPlayers_ThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(5);
        });
    }

    @Test
    void buildDeck_MinAllowedPlayers_ReturnsCorrect59CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(2);
        assertEquals(59, deck.size());

        int defuseCount = 0;
        for (Card card : deck.getCards()) {
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(3, defuseCount, "2 players should result in 3 defuse cards in the deck");
    }

    @Test
    void buildDeck_MaxAllowedPlayers_ReturnsCorrect57CardDeck() {
        Deck deck = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(4);
        assertEquals(57, deck.size());

        int defuseCount = 0;
        for (Card card : deck.getCards()) {
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(1, defuseCount, "4 players should result in 1 defuse card in the deck");
    }

    @Test
    void initializeFullDeck_ReturnsExactly56BaseCards() {
        List<Card> baseCards = DeckBuilder.initializeFullDeck();

        assertNotNull(baseCards, "The returned card list should not be null");
        assertEquals(56, baseCards.size(), "The base deck should start with 56 cards");

        for (Card card : baseCards) {
            assertNotEquals(CardType.EXPLODING_KITTEN, card.getType());
            assertNotEquals(CardType.DEFUSE, card.getType());
        }
    }

    @Test
    void calculateDefusesToAdd_LessThanMinimumPlayers_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.calculateDefusesToAdd(1);
        });
    }

    @Test
    void calculateDefusesToAdd_MoreThanMaximumPlayers_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeckBuilder.calculateDefusesToAdd(5);
        });
    }

    @Test
    void calculateDefusesToAdd_MinimumPlayers_ReturnsThree() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(2);
        assertEquals(3, numDefuses, "2 players should leave 3 defuses in the deck (5 total - 2 dealt)");
    }

    @Test
    void calculateDefusesToAdd_MaximumPlayers_ReturnsOne() {
        int numDefuses = DeckBuilder.calculateDefusesToAdd(4);
        assertEquals(1, numDefuses, "4 players should leave 1 defuse in the deck (5 total - 4 dealt)");
    }

    @Test
    void addPlayerDefuses_AddThreeDefuses_AppendsThreeDefuseCards() {
        List<Card> testDeck = new ArrayList<>();
        DeckBuilder.addPlayerDefuses(testDeck, 3);

        assertEquals(3, testDeck.size(), "Deck size should increase by 3 cards");

        int defuseCount = 0;
        for (Card card : testDeck) {
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(3, defuseCount, "All 3 added cards are of CardType.DEFUSE");
    }

    @Test
    void addPlayerDefuses_AddOneDefuse_AppendsOneDefuseCards() {
        List<Card> testDeck = new ArrayList<>();
        DeckBuilder.addPlayerDefuses(testDeck, 1);

        assertEquals(1, testDeck.size(), "Deck size should increase by 1 card");

        int defuseCount = 0;
        for (Card card : testDeck) {
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        assertEquals(1, defuseCount, "The one added card should be of CardType.DEFUSE");
    }

    @Test
    void shuffleDeckOnce_ChangesDeckOrder() {
        List<Card> testDeck = DeckBuilder.initializeFullDeck();
        List<CardType> originalOrder = new ArrayList<>();
        for (Card card : testDeck) {
            originalOrder.add(card.getType());
        }

        DeckBuilder.shuffleDeck(testDeck);

        List<CardType> shuffledOrder = new ArrayList<>();
        for (Card card : testDeck) {
            shuffledOrder.add(card.getType());
        }
        assertNotEquals(originalOrder, shuffledOrder);
    }

    @Test
    void shuffleDeckTwice_ChangesDeckOrderBothTimes() {
        List<Card> testDeck = DeckBuilder.initializeFullDeck();
        List<CardType> originalOrder = new ArrayList<>();
        for (Card card : testDeck) {
            originalOrder.add(card.getType());
        }

        DeckBuilder.shuffleDeck(testDeck);

        List<CardType> shuffledOrder = new ArrayList<>();
        for (Card card : testDeck) {
            shuffledOrder.add(card.getType());
        }
        assertNotEquals(originalOrder, shuffledOrder);

        DeckBuilder.shuffleDeck(testDeck);

        List<CardType> secondShuffledOrder = new ArrayList<>();
        for (Card card : testDeck) {
            secondShuffledOrder.add(card.getType());
        }
        assertNotEquals(shuffledOrder, secondShuffledOrder);
        assertNotEquals(originalOrder, secondShuffledOrder);
    }
}
