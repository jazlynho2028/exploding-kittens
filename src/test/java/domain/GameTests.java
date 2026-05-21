package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.lang.reflect.Method;

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

    @Test
    public void startGame_gameAlreadyStarted_throwsIllegalStateException() {
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

        game.startGame();
        assertTrue(game.getIsGameOngoing());

        int expectedDeckSizeBeforeFailure = game.getDrawPile().getCards().size();
        int expectedRoundCountBeforeFailure = game.getTurnManager().getRoundCounter();
        int expectedDrawCountBeforeFailure = game.getTurnManager().getCurrentDrawCount();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            game.startGame();
        });
        assertEquals("error.gameAlreadyStarted", exception.getMessage());

        Deck finalDrawPile = game.getDrawPile();
        assertEquals(expectedDeckSizeBeforeFailure, finalDrawPile.getCards().size());

        assertEquals(expectedRoundCountBeforeFailure, game.getTurnManager().getRoundCounter());
        assertEquals(expectedDrawCountBeforeFailure, game.getTurnManager().getCurrentDrawCount());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void addExplodingKittensToDrawPile_twoPlayers_addsOneKitten() {
        final int numTotalCards = 10;
        final int totalKittenCards = 1;

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

        Deck finalDrawPile = game.getDrawPile();
        assertEquals(0, finalDrawPile.getCards().size());

        game.addExplodingKittensToDrawPile();

        assertEquals(totalKittenCards, finalDrawPile.getCards().size());

        Card addedCard = finalDrawPile.removeTop();

        assertEquals("exploding_kitten-1", addedCard.getId());
        assertEquals(CardType.EXPLODING_KITTEN, addedCard.getType());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void addExplodingKittensToDrawPile_threePlayers_addsTwoKittens() {
        final int numTotalCards = 15;
        final int totalKittenCards = 2;

        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy");

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

        Deck finalDrawPile = game.getDrawPile();
        assertEquals(0, finalDrawPile.getCards().size());

        game.addExplodingKittensToDrawPile();

        assertEquals(totalKittenCards, finalDrawPile.getCards().size());

        Card kitten1 = finalDrawPile.removeTop();
        assertEquals("exploding_kitten-1", kitten1.getId());
        assertEquals(CardType.EXPLODING_KITTEN, kitten1.getType());

        Card kitten2 = finalDrawPile.removeTop();
        assertEquals("exploding_kitten-2", kitten2.getId());
        assertEquals(CardType.EXPLODING_KITTEN, kitten2.getType());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void addExplodingKittensToDrawPile_fourPlayers_addsThreeKittens() {
        final int numTotalCards = 20;
        final int totalKittenCards = 3;

        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

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

        Deck finalDrawPile = game.getDrawPile();
        assertEquals(0, finalDrawPile.getCards().size());

        game.addExplodingKittensToDrawPile();

        assertEquals(totalKittenCards, finalDrawPile.getCards().size());

        Card kitten1 = finalDrawPile.removeTop();
        assertEquals("exploding_kitten-1", kitten1.getId());
        assertEquals(CardType.EXPLODING_KITTEN, kitten1.getType());

        Card kitten2 = finalDrawPile.removeTop();
        assertEquals("exploding_kitten-2", kitten2.getId());
        assertEquals(CardType.EXPLODING_KITTEN, kitten2.getType());

        Card kitten3 = finalDrawPile.removeTop();
        assertEquals("exploding_kitten-3", kitten3.getId());
        assertEquals(CardType.EXPLODING_KITTEN, kitten3.getType());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void addExplodingKittensToDrawPile_onePlayer_gameThrowsException() {
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
    public void addExplodingKittensToDrawPile_fivePlayers_gameThrowsException() {
        List<String> names = Arrays.asList("Alice", "Bob", "Snoopy", "Woodstock", "John");

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
    public void getCurrentPlayerHandIds_emptyHand_returnsEmptyList() {
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

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);

        currentPlayer.clearHand();

        assertEquals(0, currentPlayer.getHand().size());

        List<String> resultIds = game.getCurrentPlayerHandIds();

        assertNotNull(resultIds);
        assertEquals(0, resultIds.size());

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void getCurrentPlayerHandIds_singleCardInHand_returnsListWithOneId() {
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

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        Card targetCard = new Card("defuse-5", CardType.DEFUSE);
        currentPlayer.addCardToHand(targetCard);

        assertEquals(1, currentPlayer.getHand().size());

        List<String> resultIds = game.getCurrentPlayerHandIds();

        assertNotNull(resultIds);
        assertEquals(1, resultIds.size());
        assertEquals("defuse-5", resultIds.get(0));

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void getCurrentPlayerHandIds_normalHandSize_returnsAllCardIds() {
        final int numTotalCards = 10;
        final int playerHandSize = 6;
        final int numIds = 6;
        final int cardIndex1 = 0;
        final int cardIndex2 = 1;
        final int cardIndex3 = 2;
        final int cardIndex4 = 3;
        final int cardIndex5 = 4;
        final int cardIndex6 = 5;


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

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        List<Card> expectedCards = Arrays.asList(
                new Card("exploding_kitten-1", CardType.EXPLODING_KITTEN),
                new Card("defuse-1", CardType.DEFUSE),
                new Card("defuse-2", CardType.DEFUSE),
                new Card("defuse-3", CardType.DEFUSE),
                new Card("feral_cat-1", CardType.FERAL_CAT),
                new Card("feral_cat-2", CardType.FERAL_CAT)
        );

        for (Card card : expectedCards) {
            currentPlayer.addCardToHand(card);
        }

        assertEquals(playerHandSize, currentPlayer.getHand().size());

        List<String> resultIds = game.getCurrentPlayerHandIds();

        assertNotNull(resultIds);
        assertEquals(numIds, resultIds.size());
        assertEquals("exploding_kitten-1", resultIds.get(cardIndex1));
        assertEquals("defuse-1", resultIds.get(cardIndex2));
        assertEquals("defuse-2", resultIds.get(cardIndex3));
        assertEquals("defuse-3", resultIds.get(cardIndex4));
        assertEquals("feral_cat-1", resultIds.get(cardIndex5));
        assertEquals("feral_cat-2", resultIds.get(cardIndex6));

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void canPlaySelected_zeroCardsSelected_returnsFalse() {
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

        game.getTurnManager().setCurrentPlayerIndex(0);
        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        boolean result = game.canPlaySelected();

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void canPlaySelected_oneCardSelected_returnsTrue() {
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

        game.getTurnManager().setCurrentPlayerIndex(0);

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        Card actionCard = new Card("skip-1", CardType.ATTACK);

        actionCard.setIsSelected(true);

        currentPlayer.addCardToHand(actionCard);

        assertEquals(1, currentPlayer.getHand().size());
        assertTrue(currentPlayer.getHand().get(0).getIsSelected());

        boolean result = game.canPlaySelected();

        assertTrue(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void canPlaySelected_twoCardsSelected_returnsTrue() {
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

        game.getTurnManager().setCurrentPlayerIndex(0);

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        Card catCard1 = new Card("cat_card-1", CardType.FERAL_CAT);
        Card catCard2 = new Card("cat_card-2", CardType.FERAL_CAT);

        catCard1.setIsSelected(true);
        catCard2.setIsSelected(true);

        currentPlayer.addCardToHand(catCard1);
        currentPlayer.addCardToHand(catCard2);

        assertEquals(2, currentPlayer.getHand().size());
        assertTrue(currentPlayer.getHand().get(0).getIsSelected());
        assertTrue(currentPlayer.getHand().get(1).getIsSelected());

        boolean result = game.canPlaySelected();

        assertTrue(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void canPlaySelected_threeCardsSelected_returnsTrue() {
        final int numTotalCards = 10;
        final int numCardsInHand = 3;

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

        game.getTurnManager().setCurrentPlayerIndex(0);

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        Card catCard1 = new Card("cat_card-1", CardType.CAT_CARD_1);
        Card catCard2 = new Card("cat_card-2", CardType.CAT_CARD_1);
        Card catCard3 = new Card("cat_card-3", CardType.CAT_CARD_1);

        catCard1.setIsSelected(true);
        catCard2.setIsSelected(true);
        catCard3.setIsSelected(true);

        currentPlayer.addCardToHand(catCard1);
        currentPlayer.addCardToHand(catCard2);
        currentPlayer.addCardToHand(catCard3);

        assertEquals(numCardsInHand, currentPlayer.getHand().size());
        assertTrue(currentPlayer.getHand().get(0).getIsSelected());
        assertTrue(currentPlayer.getHand().get(1).getIsSelected());
        assertTrue(currentPlayer.getHand().get(2).getIsSelected());

        boolean result = game.canPlaySelected();

        assertTrue(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void canPlaySelected_fourCardsSelected_returnsFalse() {
        final int numTotalCards = 10;
        final int cardIndex1 = 0;
        final int cardIndex2 = 1;
        final int cardIndex3 = 2;
        final int cardIndex4 = 3;
        final int totalCardCount = 4;

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

        game.getTurnManager().setCurrentPlayerIndex(0);

        int activePlayerIndex = game.getCurrentPlayerIndex();
        Player currentPlayer = game.getPlayers().get(activePlayerIndex);
        currentPlayer.clearHand();

        Card card1 = new Card("cat_card-1", CardType.CAT_CARD_1);
        Card card2 = new Card("cat_card-2", CardType.CAT_CARD_1);
        Card card3 = new Card("cat_card-3", CardType.CAT_CARD_1);
        Card card4 = new Card("attack-1", CardType.ATTACK);

        card1.setIsSelected(true);
        card2.setIsSelected(true);
        card3.setIsSelected(true);
        card4.setIsSelected(true);

        currentPlayer.addCardToHand(card1);
        currentPlayer.addCardToHand(card2);
        currentPlayer.addCardToHand(card3);
        currentPlayer.addCardToHand(card4);

        assertEquals(totalCardCount, currentPlayer.getHand().size());
        assertTrue(currentPlayer.getHand().get(cardIndex1).getIsSelected());
        assertTrue(currentPlayer.getHand().get(cardIndex2).getIsSelected());
        assertTrue(currentPlayer.getHand().get(cardIndex3).getIsSelected());
        assertTrue(currentPlayer.getHand().get(cardIndex4).getIsSelected());

        boolean result = game.canPlaySelected();

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_zeroCardsProvided_returnsFalse() throws Exception {
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

        List<Card> emptySelectionList = new ArrayList<>();

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);

        boolean result = (boolean) targetMethod.invoke(game, emptySelectionList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_twoCardsProvided_returnsFalse() throws Exception {
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

        Card actionCard1 = new Card("attack-1", CardType.ATTACK);
        Card actionCard2 = new Card("attack-2", CardType.ATTACK);
        List<Card> twoCardsList = Arrays.asList(actionCard1, actionCard2);

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, twoCardsList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_singleExplodingKitten_returnsFalse() throws Exception {
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

        Card explodingKitten = new Card("exploding_kitten-1", CardType.EXPLODING_KITTEN);
        List<Card> singleKittenList = Arrays.asList(explodingKitten);

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, singleKittenList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_singleDefuseCard_returnsFalse() throws Exception {
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

        Card defuseCard = new Card("defuse-1", CardType.DEFUSE);
        List<Card> singleDefuseList = Arrays.asList(defuseCard);

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, singleDefuseList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_singleCatCard_returnsFalse() throws Exception {
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

        Card catCard = new Card("cat_card-1", CardType.CAT_CARD_1);
        List<Card> singleCatList = Arrays.asList(catCard);

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);

        boolean result = (boolean) targetMethod.invoke(game, singleCatList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidOneCard_singleValidActionCard_returnsTrue() throws Exception {
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
        Card validCard = new Card("feral_cat-1", CardType.FERAL_CAT);
        List<Card> singleCardList = Arrays.asList(validCard);

        Method targetMethod = Game.class.getDeclaredMethod("isValidOneCard", List.class);
        targetMethod.setAccessible(true);

        boolean result = (boolean) targetMethod.invoke(game, singleCardList);

        assertTrue(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isCatCard_exactMatchCatCard_returnsTrue() throws Exception {
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

        Card exactCatCard = new Card("temp-id", CardType.CAT_CARD_1);

        Method targetMethod = Game.class.getDeclaredMethod("isCatCard", Card.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, exactCatCard);

        assertTrue(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isCatCard_nameContainsCatCard_returnsTrue() throws Exception {
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

        Card feralCatCard = new Card("temp-id", CardType.FERAL_CAT);

        Method targetMethod = Game.class.getDeclaredMethod("isCatCard", Card.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, feralCatCard);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isCatCard_nameHasSubstring_returnsFalse() throws Exception {
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

        Card catomicBombCard = new Card("temp-id", CardType.CATOMIC_BOMB);

        Method targetMethod = Game.class.getDeclaredMethod("isCatCard", Card.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, catomicBombCard);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isCatCard_noOverlap_returnsFalse() throws Exception {
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

        Card attackCard = new Card("temp-id", CardType.ATTACK);

        Method targetMethod = Game.class.getDeclaredMethod("isCatCard", Card.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, attackCard);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void isValidTwoCards_oneCardProvided_returnsFalse() throws Exception {
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

        Card singleCard = new Card("test-id", CardType.CAT_CARD_1);
        List<Card> insufficientList = Arrays.asList(singleCard);

        Method targetMethod = Game.class.getDeclaredMethod("isValidTwoCards", List.class);
        targetMethod.setAccessible(true);
        boolean result = (boolean) targetMethod.invoke(game, insufficientList);

        assertFalse(result);

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }

    @Test
    public void testConstructor_TooFewPlayers() {
        List<String> names = Collections.singletonList("Alice");

        Deck mockDrawPile = EasyMock.createMock(Deck.class);
        Deck mockDiscardPile = EasyMock.createMock(Deck.class);

        EasyMock.replay(mockDrawPile, mockDiscardPile);

        assertThrows(IllegalArgumentException.class, () -> {
            new Game(names, mockDrawPile, mockDiscardPile);
        });

        EasyMock.verify(mockDrawPile, mockDiscardPile);
    }
}
