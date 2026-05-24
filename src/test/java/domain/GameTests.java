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
        List<String> names = Arrays.asList("Alice", "Bob");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);

        assertEquals(2, game.getPlayers().size());
        assertFalse(game.getIsGameOngoing());
        assertFalse(game.canDraw());
        assertFalse(game.getIsFaceUp());

        assertSame(mockDrawPile, game.getDrawPile());
        assertSame(mockDiscardPile, game.getDiscardPile());

        assertNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_maximumPlayers_initializesGameCorrectly() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Woodstock");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);

        assertEquals(names.size(), game.getPlayers().size());
        assertFalse(game.getIsGameOngoing());
        assertFalse(game.canDraw());
        assertFalse(game.getIsFaceUp());

        assertSame(mockDrawPile, game.getDrawPile());
        assertSame(mockDiscardPile, game.getDiscardPile());

        assertNull(game.getTurnManager());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooLittlePlayers_throwsGameException() {
        List<String> names = Collections.singletonList("Alice");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooManyPlayers_throwsGameException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Woodstock", "Jim");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_nullPlayerList_throwsGameException() {
        List<String> names = null;
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
        });

        assertEquals("error.playerListNull", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_minimumPlayers_initializesGameAndDecks() {
        final int totalDraws = 14;
        final int totalCardsAddedToDeck = 5;
        final int defuseCards = 4;
        final int finalHandSize = 8;
        final int drawPileDefuses = 4;
        final int drawPileExp = 1;

        List<String> names = Arrays.asList("Alice", "Bob");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);

        Card genericCard = new Card(CardType.FERAL_CAT);
        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(totalDraws);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(totalCardsAddedToDeck);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN)).andReturn(1);
        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.DEFUSE)).andReturn(defuseCards);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(2, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(finalHandSize, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        assertEquals(drawPileExp, game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN));
        assertEquals(drawPileDefuses, game.getDrawPile().getCountOfCardType(CardType.DEFUSE));

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_maximumPlayers_initializesGameAndDecks() {
        final int totalCardDraws = 28;
        final int totalCardsAddedToDeck = 5;
        final int expectedExplodingKittenCount = 3;
        final int numPlayers = 4;
        final int handSize = 8;
        final int expectedDrawExp = 3;
        final int expectedDrawDefuse = 2;

        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Jim");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);

        Card genericCard = new Card(CardType.FERAL_CAT);

        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(totalCardDraws);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(totalCardsAddedToDeck);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        int actualExplodingKittens = mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN);

        EasyMock.expect(actualExplodingKittens).andReturn(expectedExplodingKittenCount);
        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.DEFUSE)).andReturn(2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(numPlayers, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(handSize, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        int actualDrawExp = game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN);
        int actualDrawDefuse = game.getDrawPile().getCountOfCardType(CardType.DEFUSE);

        assertEquals(expectedDrawExp, actualDrawExp);
        assertEquals(expectedDrawDefuse, actualDrawDefuse);

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_moreThanOnePlayer_initializesGameAndDecks() {
        final int totalInitialDraws = 21;
        final int totalCardsAdded = 5;
        final int expectedDrawExp = 2;
        final int expectedDrawDefuse = 3;
        final int numPlayers = 3;
        final int playerHandSize = 8;
        final int expectedDrawPileExp = 2;
        final int expectedDrawPileDefuse = 3;

        List<String> names = Arrays.asList("Alice", "Bob", "Jim");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);

        Card genericCard = new Card(CardType.FERAL_CAT);

        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(totalInitialDraws);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(totalCardsAdded);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        int actualDrawExp = mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN);
        EasyMock.expect(actualDrawExp).andReturn(expectedDrawExp);
        int actualDrawDefuse = mockDrawPile.getCountOfCardType(CardType.DEFUSE);
        EasyMock.expect(actualDrawDefuse).andReturn(expectedDrawDefuse);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(numPlayers, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(playerHandSize, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        int actualDrawPileExp = game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN);
        int actualDrawPileDefuse = game.getDrawPile().getCountOfCardType(CardType.DEFUSE);

        assertEquals(expectedDrawPileExp, actualDrawPileExp);
        assertEquals(expectedDrawPileDefuse, actualDrawPileDefuse);

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_tooLittlePlayers_throwsGameException() {
        List<String> names = Collections.singletonList("Alice");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
            game.startGame();
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_tooManyPlayers_throwsGameException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Jim", "Wood");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
            game.startGame();
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void startGame_gameIsAlreadyOngoing_throwsGameException() {
        final int totalDraws = 14;
        final int totalAdds = 4;

        List<String> names = Arrays.asList("Alice", "Bob");
        List<String> dummyCardIds = Arrays.asList("card-1", "card-2", "card-3");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);
        Card mockCard = EasyMock.createMock(Card.class);

        EasyMock.expect(mockDrawPile.removeTop()).andReturn(mockCard).times(totalDraws);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(totalAdds);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(1);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        EasyMock.replay(mockDrawPile, mockDiscardPile, mockCard);

        Game game = new Game(names, mockDrawPile, mockDiscardPile, dummyCardIds);
        game.startGame();

        GameException exception = assertThrows(GameException.class, () -> {
            game.startGame();
        });

        assertEquals("error.gameAlreadyStarted", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile, mockCard);
    }
}
