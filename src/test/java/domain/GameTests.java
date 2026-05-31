package domain;

import org.easymock.EasyMock;

import org.easymock.IArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static domain.GameConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

	@ParameterizedTest
	@CsvSource({
			"1, error.minPlayers",
			"5, error.maxPlayers"
	})
	public void constructor_invalidNumPlayers_failed(int numPlayers, String expectedMsg) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(players.size()).andReturn(numPlayers);

		EasyMock.replay(players);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				new Game(players, drawPile, discardPile, turnManager));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(players);
	}

	@ParameterizedTest
	@CsvSource({
			"2",
			"4"
	})
	public void constructor_validNumPlayers_initializeGame(int numPlayers) {
		List<Player> players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			Player player = EasyMock.createMock(Player.class);
			players.add(player);

			player.addCardToHand(mockSpecificCard(CardType.DEFUSE, NUM_DEFUSES - i));
			EasyMock.expectLastCall();
		}

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (Player player : players) {
			for (int i = 0; i < STARTING_HAND_SIZE - 1; i++) {
				Card card = EasyMock.createMock(Card.class);
				EasyMock.expect(drawPile.removeTop()).andReturn(card);

				player.addCardToHand(card);
				EasyMock.expectLastCall();

				EasyMock.replay(card);
			}
		}

		List<Object> mocks = new ArrayList<>(players);
		mocks.add(drawPile);

		Object[] mocksArray = mocks.toArray();

		EasyMock.replay(mocksArray);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.getIsGameOngoing());
		assertFalse(game.getIsFaceUp());

		EasyMock.verify(mocksArray);
	}

	@Test
	public void constructor_drawPileThrowsException_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.emptyDeck";

		EasyMock.expect(drawPile.removeTop()).andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(player1, player2, drawPile);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				new Game(players, drawPile, discardPile, turnManager));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(player1, player2, drawPile);
	}

	@Test
	public void startGame_gameIsOngoing_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getIsGameOngoing")
				.createMock();

		EasyMock.expect(game.getIsGameOngoing()).andReturn(true);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::startGame);

		String expectedMsg = "error.gameAlreadyStarted";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game);
	}

	@ParameterizedTest
	@CsvSource({
			"2, 1",
			"4, 3"
	})
	public void startGame_gameIsNotOngoing_startFirstRound(int numPlayers, int numKittens) {
		final int STARTING_ROUND_COUNT = 1;
		final int STARTING_DRAW_COUNT = 1;

		List<Player> players = new ArrayList<>();
		for (int i = 0; i < numPlayers; i++) {
			players.add(EasyMock.createNiceMock(Player.class));
		}

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (int i = 0; i < numPlayers * (STARTING_HAND_SIZE - 1); i++) {
			Card card = EasyMock.createNiceMock(Card.class);
			EasyMock.expect(drawPile.removeTop()).andReturn(card);
			EasyMock.replay(card);
		}

		for (int i = 1; i <= numKittens; i++) {
			drawPile.addCard(mockSpecificCard(CardType.EXPLODING_KITTEN, i));
			EasyMock.expectLastCall();
		}
		drawPile.shuffle();
		EasyMock.expectLastCall();

		Object[] playerMocks = players.toArray();
		EasyMock.replay(playerMocks);
		EasyMock.replay(drawPile);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.startGame();

		assertTrue(game.getIsGameOngoing());
		assertEquals(STARTING_ROUND_COUNT, game.getRoundCount());
		assertEquals(STARTING_DRAW_COUNT, game.getDrawCount());

		EasyMock.verify(playerMocks);
		EasyMock.verify(drawPile);
	}

	@ParameterizedTest
	@MethodSource("providePlayerName")
	public void getPlayerNames_validNPlayers_returnNNames(List<String> expectedNames) {
		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Player> players = new ArrayList<>();

		for (String name : expectedNames) {
			Player player = EasyMock.createNiceMock(Player.class);
			EasyMock.expect(player.getName()).andStubReturn(name);
			EasyMock.replay(player);

			players.add(player);
		}

		EasyMock.replay(drawPile);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		List<String> actualNames = game.getPlayerNames();

		assertEquals(expectedNames, actualNames);
	}

	private static Stream<Arguments> providePlayerName() {
		return Stream.of(
				Arguments.of(List.of("Alice", "Bob")),
				Arguments.of(List.of("Alice", "Alice", "Audrey", "Turkey"))
		);
	}

	@Test
	public void getCurrentPlayerIndex_called_success() {
		final int EXPECTED_INDEX = 2;

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		EasyMock.expect(turnManager.getCurrentPlayerIndex()).andReturn(EXPECTED_INDEX);
		EasyMock.replay(turnManager);

		int actualIndex = game.getCurrentPlayerIndex();

		assertEquals(EXPECTED_INDEX, actualIndex);

		EasyMock.verify(turnManager);
	}

	@Test
	public void getStartingPlayerIndex_called_success() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		int actualIndex = game.getStartingPlayerIndex();

		assertEquals(STARTING_PLAYER_INDEX, actualIndex);
	}

	@ParameterizedTest
	@MethodSource("provideHandIds")
	public void getCurrentPlayerHandIds_called_returnHandIds(List<String> ids) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.getHandIds()).andReturn(ids);

		EasyMock.replay(player1, player2, drawPile);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		List<String> actualIds = game.getCurrentPlayerHandIds();

		assertEquals(ids, actualIds);

		EasyMock.verify(player1, player2, drawPile, game);
	}

	private static Stream<Arguments> provideHandIds() {
		return Stream.of(
				Arguments.of(List.of()),
				Arguments.of(List.of("SKIP_1")),
				Arguments.of(List.of("SKIP_1", "SKIP_2")),
				Arguments.of(List.of("SKIP_1", "SKIP_1")),
				Arguments.of(List.of("SKIP_1", "ATTACK_3"))
		);
	}

	@Test
	public void getCurrentPlayer_called_returnCurrentPlayer() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Player actualPlayer = game.getCurrentPlayer();

		assertEquals(player1, actualPlayer);

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@ParameterizedTest
	@MethodSource("provideInvalidCardSelections")
	public void canPlaySelected_invalidCards_returnFalse(List<CardType> selectedCardTypes) {
		List<Card> selectedCards = getCardMocksWithTypeExpectations(selectedCardTypes);

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		assertFalse(game.canPlaySelected());

		EasyMock.verify(player1, player2, drawPile, game);
	}

	private static Stream<Arguments> provideInvalidCardSelections() {
		return Stream.of(
				Arguments.of(List.of()),
				Arguments.of(List.of(CardType.DEFUSE)),
				Arguments.of(List.of(CardType.EXPLODING_KITTEN)),
				Arguments.of(List.of(CardType.CAT_CARD_1)),
				Arguments.of(List.of(CardType.CAT_CARD_2)),
				Arguments.of(List.of(CardType.CAT_CARD_3)),
				Arguments.of(List.of(CardType.CAT_CARD_4)),
				Arguments.of(List.of(CardType.FERAL_CAT))
		);
	}

	private List<Card> getCardMocksWithTypeExpectations(List<CardType> cardTypes) {
		List<Card> selectedCards = new ArrayList<>();

		for (CardType cardType : cardTypes) {
			Card card = EasyMock.createMock(Card.class);
			EasyMock.expect(card.getType()).andReturn(cardType);
			EasyMock.replay(card);

			selectedCards.add(card);
		}

		return selectedCards;
	}

	@ParameterizedTest
	@MethodSource("provideValidCardSelections")
	public void canPlaySelected_validCards_returnTrue(List<CardType> selectedCardTypes) {
		List<Card> selectedCards = getCardMocksWithTypeExpectations(selectedCardTypes);

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(player1, player2, drawPile);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		assertTrue(game.canPlaySelected());

		List<Object> mocks = new ArrayList<>(selectedCards);
		Collections.addAll(mocks, player1, player2, drawPile, game);
		Object[] mocksArray = mocks.toArray();

		EasyMock.verify(mocksArray);
	}

	private static Stream<Arguments> provideValidCardSelections() {
		return Stream.of(
				Arguments.of(List.of(CardType.ATTACK)),
				Arguments.of(List.of(CardType.SHUFFLE)),
				Arguments.of(List.of(CardType.SKIP)),
				Arguments.of(List.of(CardType.SEE_THE_FUTURE)),
				Arguments.of(List.of(CardType.CATOMIC_BOMB)),
				Arguments.of(List.of(CardType.SUPER_SKIP)),
				Arguments.of(List.of(CardType.GODCAT)),
				Arguments.of(List.of(CardType.CLONE)),
				Arguments.of(List.of(CardType.SWAP_TOP_AND_BOTTOM)),
				Arguments.of(List.of(CardType.DRAW_FROM_THE_BOTTOM)),
				Arguments.of(List.of(CardType.TARGETED_ATTACK)),
				Arguments.of(List.of(CardType.WINNER_WINNER_CATNIP_DINNER)),
				Arguments.of(List.of(CardType.RAGEBAIT)),
				Arguments.of(List.of(CardType.RECYCLE)),
				Arguments.of(List.of(CardType.DOUBLE_UP)),
				Arguments.of(List.of(CardType.MILD_DRAW))
		);
	}

	@Test
	public void playSelectedCards_invalidPlay_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.createMock();

		EasyMock.expect(game.canPlaySelected()).andReturn(false);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::playSelectedCards);

		String expectedMsg = "error.cannotPlaySelectedCards";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(player1, player2, drawPile, game);
	}

	@ParameterizedTest
	@MethodSource("provideValidPlaysAndMethods")
	public void playSelectedCards_validPlay_cardsMovedFromHandToDiscard(
			CardType cardType, String applyMethodName, Consumer<Game> applyMethod) {

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createNiceMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andReturn(cardType);
		List<Card> selectedCards = List.of(card);

		card.toggleSelected();
		EasyMock.expectLastCall();

		EasyMock.expect(player1.getSelectedCards()).andReturn(selectedCards).anyTimes();

		player1.removeCardFromHand(card);
		EasyMock.expectLastCall();

		discardPile.addCard(card);
		EasyMock.expectLastCall();

		EasyMock.replay(player1, player2, drawPile, discardPile, card);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod(applyMethodName)
				.createMock();

		EasyMock.expect(game.canPlaySelected()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1).anyTimes();

		applyMethod.accept(game);
		EasyMock.expectLastCall();

		EasyMock.replay(game);

		game.playSelectedCards();

		EasyMock.verify(player1, player2, drawPile, discardPile, card, game);
	}

	private static Stream<Arguments> provideValidPlaysAndMethods() {
		return Stream.of(
				Arguments.of(CardType.ATTACK, "applyAttack", (Consumer<Game>) Game::applyAttack),
				Arguments.of(CardType.SHUFFLE, "applyShuffle", (Consumer<Game>) Game::applyShuffle)
		);
	}

	@ParameterizedTest
	@CsvSource({
			"false, 0",
			"false, 1",
			"false, 2",
			"true, 1",
			"true, 2"
	})
	public void canEndTurn_called_returnFalse(boolean isGameOngoing, int drawCount) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getIsGameOngoing")
				.addMockedMethod("getDrawCount")
				.createMock();

		EasyMock.expect(game.getIsGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(game.getDrawCount()).andReturn(drawCount).anyTimes();

		EasyMock.replay(game);

		assertFalse(game.canEndTurn());

		EasyMock.verify(game);
	}

	@Test
	public void canEndTurn_gameIsOngoingAndDrawCountZero_returnTrue() {
		final int DRAW_COUNT = 0;

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getIsGameOngoing")
				.addMockedMethod("getDrawCount")
				.createMock();

		EasyMock.expect(game.getIsGameOngoing()).andReturn(true);
		EasyMock.expect(game.getDrawCount()).andReturn(DRAW_COUNT);

		EasyMock.replay(game);

		assertTrue(game.canEndTurn());

		EasyMock.verify(game);
	}

	@Test
	public void isDrawPileEmpty_emptyDrawPile_returnTrue() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertTrue(game.isDrawPileEmpty());

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@ParameterizedTest
	@MethodSource("provideNonEmptyDrawPiles")
	public void isDrawPileEmpty_called_returnFalse(List<Card> drawPileCards) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.isDrawPileEmpty());

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	private static Stream<Arguments> provideNonEmptyDrawPiles() {
		return Stream.of(
				Arguments.of(List.of(CardType.SKIP)),
				Arguments.of(List.of(CardType.SKIP, CardType.SKIP)),
				Arguments.of(List.of(CardType.SKIP, CardType.ATTACK))
				);
	}

	@ParameterizedTest
	@CsvSource({
			"false, 0",
			"false, 1",
			"false, 2",
			"true, 0"
	})
	public void getCanDraw_called_returnFalse(boolean isGameOngoing, int drawCount) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getIsGameOngoing")
				.addMockedMethod("getDrawCount")
				.createMock();

		EasyMock.expect(game.getIsGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(game.getDrawCount()).andReturn(drawCount).anyTimes();

		EasyMock.replay(game);

		assertFalse(game.getCanDraw());

		EasyMock.verify(game);
	}

	@ParameterizedTest
	@CsvSource({
			"1",
			"2"
	})
	public void getCanDraw_called_returnTrue(int drawCount) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getIsGameOngoing")
				.addMockedMethod("getDrawCount")
				.createMock();

		EasyMock.expect(game.getIsGameOngoing()).andReturn(true);
		EasyMock.expect(game.getDrawCount()).andReturn(drawCount);

		EasyMock.replay(game);

		assertTrue(game.getCanDraw());

		EasyMock.verify(game);
	}

	@Test
	public void changeCurrentPlayerIndex_called_callsTurnManager() {
		final int NEW_PLAYER_INDEX = 0;

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.setCurrentPlayerIndex(NEW_PLAYER_INDEX);
		EasyMock.expectLastCall();

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.changeCurrentPlayerIndex(NEW_PLAYER_INDEX);

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void setFaceUpToFalse_called_setToFalse(boolean initialFaceUp) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setIsFaceUp(initialFaceUp);
		game.setFaceUpToFalse();

		assertFalse(game.getIsFaceUp());

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@Test
	public void drawFromPile_emptyDrawPile_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (int i = 0; i < MIN_PLAYERS * (STARTING_HAND_SIZE - 1); i++) {
			Card card = EasyMock.createNiceMock(Card.class);
			EasyMock.expect(drawPile.removeTop()).andReturn(card);
			EasyMock.replay(card);
		}

		EasyMock.expect(drawPile.removeTop()).andThrow(
				new IllegalStateException("error.emptyDeck")
		);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		Exception exception = assertThrows(IllegalStateException.class, game::drawFromPile);
		assertEquals("error.emptyDeck", exception.getMessage());

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@Test
	public void drawFromPile_drawCountAtZero_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.negativeDrawCount";

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(player1);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class,
				game::drawFromPile);
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game, drawPile, turnManager);
	}

	@Test
	public void drawFromPile_oneCardInDrawPile_addToCurrentPlayerHand() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (int i = 0; i < MIN_PLAYERS * (STARTING_HAND_SIZE - 1); i++) {
			Card card = EasyMock.createNiceMock(Card.class);
			EasyMock.expect(drawPile.removeTop()).andReturn(card);
			EasyMock.replay(card);
		}

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(drawnCard);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		player1.addCardToHand(drawnCard);
		EasyMock.expectLastCall();

		player1.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(player1, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1).anyTimes();

		EasyMock.replay(game);

		game.drawFromPile();

		EasyMock.verify(player1, drawPile, turnManager, game);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void toggleFaceUp_called_togglesFaceUp(boolean initialFaceUp) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setIsFaceUp(initialFaceUp);
		game.toggleFaceUp();

		assertEquals(!initialFaceUp, game.getIsFaceUp());

		EasyMock.verify(player1, player2, drawPile, turnManager);
	}

	@ParameterizedTest
	@CsvSource({
			"0",
			"1"
	})
	public void toggleSelectedCurrentPlayerCardAt_called_calledPlayerToggle(int handCardIndex) {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		player1.toggleSelectedHandCardAt(handCardIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(player1, player2, drawPile);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		game.toggleSelectedPlayerCardAt(handCardIndex);

		EasyMock.verify(player1, player2, drawPile, game);
	}

	@Test
	public void toggleSelectedCurrentPlayerCardAt_indexZero_failed() {
		final int HAND_CARD_INDEX = 0;
		String expectedMsg = "error.handCardIndexOutOfBounds";

		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		player1.toggleSelectedHandCardAt(HAND_CARD_INDEX);
		EasyMock.expectLastCall().andThrow(new IllegalArgumentException(expectedMsg));

		EasyMock.replay(player1, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalArgumentException.class,
				() -> game.toggleSelectedPlayerCardAt(HAND_CARD_INDEX));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(player1, drawPile, turnManager, game);
	}

	@Test
	public void advanceTurn_canEndTurn_advanceTurnAndDeselectCards() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.advanceTurn();
		EasyMock.expectLastCall();

		player1.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1);

		EasyMock.replay(game);

		game.advanceTurn();

		EasyMock.verify(player1, player2, drawPile, turnManager, game);
	}

	@Test
	public void advanceTurn_cannotEndTurn_failed() {
		Player player1 = EasyMock.createNiceMock(Player.class);
		Player player2 = EasyMock.createNiceMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(player1, player2, drawPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);
		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::advanceTurn);
		assertEquals("error.cannotEndTurn", exception.getMessage());

		EasyMock.verify(game);
	}

	private static Card mockSpecificCard(CardType cardType, int idNum) {
		EasyMock.reportMatcher(new IArgumentMatcher() {
			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof Card)) {
					return false;
				}

				Card card = (Card) argument;
				return hasSameCardFields(card, cardType, idNum);
			}

			@Override
			public void appendTo(StringBuffer buffer) {
				buffer.append(
						String.format("isCardOfTypeAndId(%s, %d)",
								cardType,
								idNum));
			}
		});
		return new Card("INVALID_CARD_MOCK", cardType);
	}

	private static boolean hasSameCardFields(Card card, CardType cardType, int idNum) {
		boolean matchesType = (card.getType() == cardType);
		String normalizedTypeName = cardType.name().replace("_", "");
		String expectedId = String.format("%s_%d", normalizedTypeName, idNum);

		boolean matchesId = Objects.equals(card.getId(), expectedId);

		return matchesType && matchesId;
	}

}