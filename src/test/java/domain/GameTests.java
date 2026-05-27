package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void constructor_minimumPlayers_initializesGameCorrectly() {
        final int totalHandSize = 6;
        final int totalPlayers = 2;
        final int normalCards = 5;

        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();

        int cardsToDraw = normalCards * names.size();
        for (int i = 0; i < cardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);
        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        for (int i = 0; i < totalPlayers; i++) {
            Player player = game.getPlayers().get(i);

            assertEquals(names.get(i), player.getName());
            assertEquals(totalHandSize, player.getHand().size());
            assertEquals(CardType.DEFUSE, player.getHand().get(0).getType());
        }

        assertFalse(game.getIsGameOngoing());
        assertFalse(game.getIsFaceUp());
        assertNotNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_maximumPlayers_initializesGameCorrectly() {
        final int totalHandSize = 6;
        final int totalPlayers = 4;
        final int normalCards = 5;

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();

        int totalCardsToDraw = normalCards * names.size();
        for (int i = 0; i < totalCardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);
        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        for (int i = 0; i < totalPlayers; i++) {
            Player player = game.getPlayers().get(i);

            assertEquals(names.get(i), player.getName());
            assertEquals(totalHandSize, player.getHand().size());
            assertEquals(CardType.DEFUSE, player.getHand().get(0).getType());
        }

        assertFalse(game.getIsGameOngoing());
        assertFalse(game.getIsFaceUp());
        assertNotNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooLittlePlayers_throwsIllegalArgumentException() {
        List<String> names = Arrays.asList("Alice");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });
        assertEquals("error.invalidPlayerCount", exception.getMessage());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooManyPlayers_throwsIllegalArgumentException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "Dave", "Eve");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });
        assertEquals("error.invalidPlayerCount", exception.getMessage());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void populatePlayerHands_minimumPlayers_allocatesCorrectCards() {
        final int totalHandSize = 6;
        final int totalPlayers = 2;
        final int normalCardsPerPlayer = 5;

        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();
        int totalCardsToDraw = normalCardsPerPlayer * names.size();
        for (int i = 0; i < totalCardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        assertEquals(totalHandSize, player1.getHand().size());
        assertEquals("defuse-5", player1.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player1.getHand().get(0).getType());

        assertEquals(totalHandSize, player2.getHand().size());
        assertEquals("defuse-4", player2.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player2.getHand().get(0).getType());

        assertEquals(0, game.getDrawPile().getCards().size());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void populatePlayerHands_maximumPlayers_allocatesCorrectCards() {
        final int totalHandSize = 6;
        final int totalPlayers = 4;
        final int normalCardsPerPlayer = 5;
        final int player1Index = 0;
        final int player2Index = 1;
        final int player3Index = 2;
        final int player4Index = 3;

        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "John");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();
        int totalCardsToDraw = normalCardsPerPlayer * names.size();
        for (int i = 0; i < totalCardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        Player player1 = game.getPlayers().get(player1Index);
        Player player2 = game.getPlayers().get(player2Index);
        Player player3 = game.getPlayers().get(player3Index);
        Player player4 = game.getPlayers().get(player4Index);

        assertEquals(totalHandSize, player1.getHand().size());
        assertEquals("defuse-5", player1.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player1.getHand().get(0).getType());

        assertEquals(totalHandSize, player2.getHand().size());
        assertEquals("defuse-4", player2.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player2.getHand().get(0).getType());

        assertEquals(totalHandSize, player3.getHand().size());
        assertEquals("defuse-3", player3.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player3.getHand().get(0).getType());

        assertEquals(totalHandSize, player4.getHand().size());
        assertEquals("defuse-2", player4.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player4.getHand().get(0).getType());

        assertEquals(0, game.getDrawPile().getCards().size());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void populatePlayerHands_exactCards_emptiesDeck() {
        final int totalHandSize = 6;
        final int totalPlayers = 3;
        final int normalCardsPerPlayer = 5;

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();
        int totalCardsToDraw = normalCardsPerPlayer * names.size();
        for (int i = 0; i < totalCardsToDraw; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(totalPlayers, game.getPlayers().size());

        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        Player player3 = game.getPlayers().get(2);

        assertEquals(totalHandSize, player1.getHand().size());
        assertEquals("defuse-5", player1.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player1.getHand().get(0).getType());

        assertEquals(totalHandSize, player2.getHand().size());
        assertEquals("defuse-4", player2.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player2.getHand().get(0).getType());

        assertEquals(totalHandSize, player3.getHand().size());
        assertEquals("defuse-3", player3.getHand().get(0).getId());
        assertEquals(CardType.DEFUSE, player3.getHand().get(0).getType());

        assertEquals(0, game.getDrawPile().getCards().size());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void populatePlayerHands_insufficientCards_throwsException() {
        final int normalCardsPerPlayer = 5;

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> drawPileCards = new ArrayList<>();
        int insufficientCardsCount = (normalCardsPerPlayer * names.size()) - 1;

        for (int i = 0; i < insufficientCardsCount; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            drawPileCards.add(mockCard);
        }

        List<Card> dummyCards2 = Arrays.asList(EasyMock.createMock(Card.class));

        EasyMock.expect(mockDrawPile.getCards()).andReturn(drawPileCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(dummyCards2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });
        assertEquals("error.emptyDrawPile", exception.getMessage());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_gameNotStarted_initializesGameSuccessfully() {
        final int numTotalCards = 10;

        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        List<Card> initialCards = new ArrayList<>();
        for (int i = 0; i < numTotalCards; i++) {
            Card mockCard = EasyMock.createMock(Card.class);
            EasyMock.replay(mockCard);
            initialCards.add(mockCard);
        }

        EasyMock.expect(mockDrawPile.getCards()).andReturn(initialCards);
        EasyMock.expect(mockDiscardPile.getCards()).andReturn(new ArrayList<>());

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertFalse(game.getIsGameOngoing());

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertFalse(game.canEndTurn());

        Deck finalDrawPile = game.getDrawPile();
        assertEquals(1, finalDrawPile.getCards().size());

        Card remainingCard = finalDrawPile.removeTop();
        assertEquals(CardType.EXPLODING_KITTEN, remainingCard.getType());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
