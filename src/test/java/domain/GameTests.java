package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

    @Test
    public void constructor_minimumPlayers_initializesGameCorrectly() {
        List<String> names = Arrays.asList("Alice", "Bob");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

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

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        assertEquals(4, game.getPlayers().size());
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

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_tooManyPlayers_throwsGameException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Woodstock", "Jim");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });

        assertEquals("error.invalidPlayerCount", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void constructor_nullPlayerList_throwsGameException() {
        List<String> names = null;

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        GameException exception = assertThrows(GameException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });

        assertEquals("error.playerListNull", exception.getKey());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void testStartGame_MinValidPlayersSuccess() {
        List<String> names = Arrays.asList("Alice", "Bob");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        Card genericCard = new Card(CardType.FERAL_CAT);
        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(14);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(5);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN)).andReturn(1);
        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.DEFUSE)).andReturn(4);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(2, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(8, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        assertEquals(1, game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN));
        assertEquals(4, game.getDrawPile().getCountOfCardType(CardType.DEFUSE));

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void testStartGame_MaxValidPlayersSuccess() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Jim");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        Card genericCard = new Card(CardType.FERAL_CAT);

        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(28);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(5);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN)).andReturn(3);
        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.DEFUSE)).andReturn(2);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(4, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(8, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        assertEquals(3, game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN));
        assertEquals(2, game.getDrawPile().getCountOfCardType(CardType.DEFUSE));

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void testStartGame_MoreThanOneValidPlayersSuccess() {
        List<String> names = Arrays.asList("Alice", "Bob", "Jim");
        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        Game game = new Game(names, mockDrawPile, mockDiscardPile);

        Card genericCard = new Card(CardType.FERAL_CAT);

        EasyMock.expect(mockDrawPile.removeTop()).andReturn(genericCard).times(21);

        mockDrawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().times(5);

        mockDrawPile.shuffle();
        EasyMock.expectLastCall().once();

        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.EXPLODING_KITTEN)).andReturn(2);
        EasyMock.expect(mockDrawPile.getCountOfCardType(CardType.DEFUSE)).andReturn(3);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        game.startGame();

        assertTrue(game.getIsGameOngoing());
        assertTrue(game.canDraw());
        assertEquals(3, game.getPlayers().size());

        for (Player p : game.getPlayers()) {
            assertEquals(8, p.getHand().size());
            long defuseCount = p.getHand().stream()
                    .filter(c -> c.getType() == CardType.DEFUSE)
                    .count();
            assertEquals(1, defuseCount);
        }

        assertEquals(2, game.getDrawPile().getCountOfCardType(CardType.EXPLODING_KITTEN));
        assertEquals(3, game.getDrawPile().getCountOfCardType(CardType.DEFUSE));

        assertNotNull(game.getTurnManager());
        assertEquals(0, game.getTurnManager().getCurrentPlayerIndex());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
