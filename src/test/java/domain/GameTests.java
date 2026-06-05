package domain;

import org.easymock.EasyMock;

import org.easymock.IArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static domain.GameConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTests {

	@Test
	public void constructor_anyInput_initializeFieldsFalse() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.getIsGameOngoing());
		assertFalse(game.getIsFaceUp());
	}

	@ParameterizedTest
	@CsvSource({
			"1, error.minPlayers",
			"5, error.maxPlayers"
	})
	public void setUp_invalidNumPlayers_failed(int numPlayers, String expectedMsg) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(players.size()).andReturn(numPlayers);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalArgumentException.class, game::setUp);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(players);
	}

	@ParameterizedTest
	@CsvSource({
			"2",
			"4"
	})
	public void setUp_validNumPlayers_initializeGame(int numPlayers) {
		List<Player> players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			Player player = EasyMock.createMock(Player.class);
			players.add(player);

			player.addCardToHand(mockSpecificCard(
					CardType.DEFUSE, NUM_DEFUSES_IN_GAME - i));
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

		Object[] playerMocks = players.toArray();

		EasyMock.replay(playerMocks);
		EasyMock.replay(drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setUp();

		EasyMock.verify(playerMocks);
		EasyMock.verify(drawPile);
	}

	@Test
	public void setUp_drawPileThrowsException_failed() {
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

		EasyMock.replay(player1, player2, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		Exception exception = assertThrows(IllegalStateException.class, game::setUp);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(drawPile);
	}

	@Test
	public void startGame_gameIsOngoing_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

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
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(players.size()).andStubReturn(numPlayers);

		for (int i = 1; i <= numKittens; i++) {
			drawPile.addCard(mockSpecificCard(CardType.EXPLODING_KITTEN, i));
			EasyMock.expectLastCall();
		}

		drawPile.shuffle();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.startGame();

		assertTrue(game.getIsGameOngoing());

		EasyMock.verify(drawPile);
	}

	@ParameterizedTest
	@MethodSource("providePlayerName")
	public void getPlayerNames_validNPlayers_returnNNames(List<String> expectedNames) {
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Player> players = new ArrayList<>();

		for (String name : expectedNames) {
			Player player = EasyMock.createMock(Player.class);
			EasyMock.expect(player.getName()).andStubReturn(name);
			EasyMock.replay(player);

			players.add(player);
		}

		EasyMock.replay(drawPile, discardPile, turnManager);

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

	@ParameterizedTest
	@CsvSource({
			"0",
			"1",
			"2"
	})
	public void getCurrentPlayerIndex_called_success(int expectedCurrentPlayerIndex) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getCurrentPlayerIndex())
				.andReturn(expectedCurrentPlayerIndex);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		int actualCurrentPlayerIndex = game.getCurrentPlayerIndex();

		assertEquals(expectedCurrentPlayerIndex, actualCurrentPlayerIndex);

		EasyMock.verify(turnManager);
	}

	@Test
	public void getStartingPlayerIndex_called_success() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, discardPile, drawPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		int actualStartingPlayerIndex = game.getStartingPlayerIndex();

		assertEquals(STARTING_PLAYER_INDEX, actualStartingPlayerIndex);
	}

	@ParameterizedTest
	@CsvSource({
			"0",
			"1"
	})
	public void getCurrentPlayer_called_returnCurrentPlayer(int currentPlayerIndex) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player expectedPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(turnManager.getCurrentPlayerIndex())
				.andReturn(currentPlayerIndex);

		EasyMock.expect(players.get(currentPlayerIndex)).andReturn(expectedPlayer);

		EasyMock.replay(expectedPlayer, players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Player actualPlayer = game.getCurrentPlayer();

		assertEquals(expectedPlayer, actualPlayer);

		EasyMock.verify(players, turnManager);
	}

	@Test
	public void getCurrentPlayerHandIds_called_returnPlayerMethodCall() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<String> expectedHandIds = List.of("SKIP_1", "DEFUSE_3");
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHandIds()).andReturn(expectedHandIds);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		List<String> actualHandIds = game.getCurrentPlayerHandIds();

		assertEquals(expectedHandIds, actualHandIds);

		EasyMock.verify(currentPlayer, game);
	}

	private Game createAndSetGameExpectationsWithGetCurrentPlayer(
			List<Player> players, Deck drawPile, Deck discardPile,
			TurnManager turnManager, Player currentPlayer) {

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);

		return game;
	}

	@ParameterizedTest
	@MethodSource("provideInvalidCardSelections")
	public void canPlaySelected_invalidCards_returnFalse(List<CardType> selectedCardTypes) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> selectedCards = getCardMocksWithTypeExpectations(selectedCardTypes);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertFalse(game.canPlaySelected());

		EasyMock.verify(currentPlayer, game);
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

	@ParameterizedTest
	@MethodSource("provideValidCardSelections")
	public void canPlaySelected_validCards_returnTrue(List<CardType> selectedCardTypes) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> selectedCards = getCardMocksWithTypeExpectations(selectedCardTypes);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertTrue(game.canPlaySelected());

		EasyMock.verify(currentPlayer, game);
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
				Arguments.of(List.of(CardType.MILD_SHUFFLE))
		);
	}

	@Test
	public void playSelectedCards_invalidPlay_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.createMock();

		EasyMock.expect(game.canPlaySelected()).andReturn(false);

		EasyMock.replay(game);

		Exception exception = assertThrows(
				IllegalStateException.class, game::playSelectedCards);

		String expectedMsg = "error.cannotPlaySelectedCards";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game);
	}

	@Test
	public void playSelectedCards_validPlayWithUnknownCardType_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		CardType cardType = CardType.DEFUSE;
		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(cardType);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createGameForPlaySelectedCardsExceptionCase(
				players, drawPile, discardPile, turnManager
		);

		setGameExpectationsForPlaySelectedCards(game, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(
				IllegalStateException.class, game::playSelectedCards);

		String expectedMsg = "error.cannotPlaySelectedCards";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, game, currentPlayer);
	}

	private void setMoveCardToDiscardExpectations(
			List<Card> selectedCards, Deck discardPile, Player currentPlayer) {

		for (Card selectedCard : selectedCards) {
			selectedCard.toggleSelected();
			EasyMock.expectLastCall();
			EasyMock.replay(selectedCard);

			currentPlayer.removeCardFromHand(selectedCard);
			EasyMock.expectLastCall();

			discardPile.addCard(selectedCard);
			EasyMock.expectLastCall();
		}
	}

	private Game createGameForPlaySelectedCardsExceptionCase(
			List<Player> players, Deck drawPile, Deck discardPile,
			TurnManager turnManager) {

		return EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.createMock();
	}

	private void setGameExpectationsForPlaySelectedCards(
			Game game, Player currentPlayer) {

		EasyMock.expect(game.canPlaySelected()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);
	}

	@Test
	public void playSelectedCards_validPlay_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createNiceMock(Deck.class);
		TurnManager turnManager = EasyMock.createNiceMock(TurnManager.class);

		Card card = EasyMock.createNiceMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(CardType.ATTACK);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		String expectedMsg = "error.cardNotInHand";
		currentPlayer.removeCardFromHand(card);
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer, card);

		Game game = createGameForPlaySelectedCardsExceptionCase(
				players, drawPile, discardPile, turnManager
		);

		setGameExpectationsForPlaySelectedCards(game, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(
				IllegalStateException.class, game::playSelectedCards);


		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(currentPlayer, game);
	}


	@ParameterizedTest
	@MethodSource("provideValidPlaysAndMethods")
	public void playSelectedCards_validPlayWithApplyMethod_cardsMovedFromHandToDiscard(
			CardType expectedCardType, String applyMethodName,
			Consumer<Game> applyMethod) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(expectedCardType);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod(applyMethodName)
				.createMock();

		setGameExpectationsForPlaySelectedCards(game, currentPlayer);

		applyMethod.accept(game);
		EasyMock.expectLastCall();

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();

		assertEquals(expectedCardType, actualCardType);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, currentPlayer, game);
	}

	private static Stream<Arguments> provideValidPlaysAndMethods() {
		return Stream.of(
				Arguments.of(CardType.ATTACK, "applyAttack",
						(Consumer<Game>) Game::applyAttack),
				Arguments.of(CardType.SHUFFLE, "applyShuffle",
						(Consumer<Game>) Game::applyShuffle),
				Arguments.of(CardType.SKIP, "applySkip",
						(Consumer<Game>) Game::applySkip),
				Arguments.of(CardType.CATOMIC_BOMB, "applyCatomicBomb",
						(Consumer<Game>) Game::applyCatomicBomb),
				Arguments.of(CardType.SUPER_SKIP, "applySuperSkip",
						(Consumer<Game>) Game::applySuperSkip),
				Arguments.of(CardType.CLONE, "applyClone",
						(Consumer<Game>) Game::applyClone),
				Arguments.of(CardType.SWAP_TOP_AND_BOTTOM, "applySwapTopAndBottom",
						(Consumer<Game>) Game::applySwapTopAndBottom),
				Arguments.of(
						CardType.DRAW_FROM_THE_BOTTOM,
						"applyDrawFromTheBottom",
						(Consumer<Game>) Game::applyDrawFromTheBottom
				),
				Arguments.of(
						CardType.WINNER_WINNER_CATNIP_DINNER,
						"applyWinnerWinnerCatnipDinner",
						(Consumer<Game>) Game::applyWinnerWinnerCatnipDinner
				),
				Arguments.of(CardType.RAGEBAIT, "applyRagebait",
						(Consumer<Game>) Game::applyRagebait),
				Arguments.of(CardType.RECYCLE, "applyRecycle",
						(Consumer<Game>) Game::applyRecycle),
				Arguments.of(CardType.DOUBLE_UP, "applyDoubleUp",
						(Consumer<Game>) Game::applyDoubleUp),
				Arguments.of(CardType.MILD_SHUFFLE, "applyMildShuffle",
						(Consumer<Game>) Game::applyMildShuffle)
		);
	}

	@ParameterizedTest
	@MethodSource("provideValidCardTypesWithoutApplyMethod")
	public void playSelectedCards_validPlayWithoutApplyMethod_cardsMovedFromHandToDiscard(
			CardType expectedCardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(expectedCardType);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		setGameExpectationsForPlaySelectedCards(game, currentPlayer);

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();

		assertEquals(expectedCardType, actualCardType);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, currentPlayer, game);
	}

	private static Stream<Arguments> provideValidCardTypesWithoutApplyMethod() {
		return Stream.of(
				Arguments.of(CardType.SEE_THE_FUTURE),
				Arguments.of(CardType.GODCAT),
				Arguments.of(CardType.TARGETED_ATTACK)
		);
	}

	@Test
	public void playSelectedCards_godcatPlayed_returnsGodcat() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(CardType.GODCAT);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		setGameExpectationsForPlaySelectedCards(game, currentPlayer);

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();

		assertEquals(CardType.GODCAT, actualCardType);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, currentPlayer, game);
	}

	@Test
	public void getTopDiscardId_emptyDiscardPile_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.emptyDeck";

		EasyMock.expect(discardPile.peekTop()).andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertThrows(IllegalStateException.class, game::getTopDiscardId);

		EasyMock.verify(discardPile);
	}

	@Test
	public void getTopDiscardId_nonEmptyDiscardPile_returnTopCardId() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedId = "SKIP_1";
		Card topCard = EasyMock.createMock(Card.class);
		EasyMock.expect(topCard.getId()).andStubReturn(expectedId);

		EasyMock.expect(discardPile.peekTop()).andReturn(topCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, topCard);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		String actualId = game.getTopDiscardId();

		assertEquals(expectedId, actualId);

		EasyMock.verify(discardPile);
	}

	@Test
	public void canDrawFromDiscard_none_returnFalse() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.canDrawFromDiscard());
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
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		if (isGameOngoing) {
			EasyMock.expect(turnManager.getDrawCount()).andReturn(drawCount);
		}

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setIsGameOngoing(isGameOngoing);

		assertFalse(game.canEndTurn());

		EasyMock.verify(turnManager);
	}

	@Test
	public void canEndTurn_gameIsOngoingAndDrawCountZero_returnTrue() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(0);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsGameOngoing(true);

		assertTrue(game.canEndTurn());

		EasyMock.verify(turnManager);
	}

	@Test
	public void isDrawPileEmpty_emptyDrawPile_returnTrue() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertTrue(game.isDrawPileEmpty());

		EasyMock.verify(drawPile);
	}

	@ParameterizedTest
	@MethodSource("provideNonEmptyDrawPiles")
	public void isDrawPileEmpty_called_returnFalse() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.isDrawPileEmpty());

		EasyMock.verify(drawPile);
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
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		if (isGameOngoing) {
			EasyMock.expect(turnManager.getDrawCount()).andReturn(drawCount);
		}

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setIsGameOngoing(isGameOngoing);

		assertFalse(game.getCanDraw());

		EasyMock.verify(turnManager);
	}

	@ParameterizedTest
	@CsvSource({
			"1",
			"2"
	})
	public void getCanDraw_called_returnTrue(int drawCount) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(drawCount);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setIsGameOngoing(true);

		assertTrue(game.getCanDraw());

		EasyMock.verify(turnManager);
	}

	@Test
	public void changeCurrentPlayerIndex_called_callsTurnManager() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int newPlayerIndex = 0;
		turnManager.setCurrentPlayerIndex(newPlayerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.changeCurrentPlayerIndex(newPlayerIndex);

		EasyMock.verify(turnManager);
	}

	@Test
	public void changeCurrentPlayerIndex_called_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int newPlayerIndex = 0;
		String expectedMsg = "error.invalidPlayerIndex";

		turnManager.setCurrentPlayerIndex(newPlayerIndex);
		EasyMock.expectLastCall().andThrow(
				new IllegalArgumentException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				game.changeCurrentPlayerIndex(newPlayerIndex));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void setFaceUpToFalse_called_setToFalse(boolean initialFaceUp) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsFaceUp(initialFaceUp);

		game.setFaceUpToFalse();

		assertFalse(game.getIsFaceUp());
	}

	@Test
	public void drawFromPile_nonExplodingCard_returnsDrawnCard() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		CardType expectedCardType = CardType.ATTACK;
		String expectedCardId = "ATTACK_1";
		Card expectedCard = EasyMock.createMock(Card.class);
		EasyMock.expect(expectedCard.getType()).andStubReturn(expectedCardType);
		EasyMock.expect(expectedCard.getId()).andStubReturn(expectedCardId);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(drawPile.peekTop()).andReturn(expectedCard);
		EasyMock.expect(drawPile.removeTop()).andReturn(expectedCard);

		currentPlayer.addCardToHand(expectedCard);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				expectedCard, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromPile();
		assertEquals(expectedCard, actualCard);

		EasyMock.verify(drawPile, turnManager, currentPlayer, game);
	}

	@Test
	public void drawFromPile_explodingCard_returnsExplodingCard() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		CardType expectedCardType = CardType.EXPLODING_KITTEN;
		String expectedCardId = "EXPLODINGKITTEN_1";
		Card expectedCard = EasyMock.createMock(Card.class);
		EasyMock.expect(expectedCard.getType()).andStubReturn(expectedCardType);
		EasyMock.expect(expectedCard.getId()).andStubReturn(expectedCardId);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(drawPile.peekTop()).andReturn(expectedCard);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				expectedCard, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromPile();
		assertEquals(expectedCard, actualCard);

		EasyMock.verify(drawPile, turnManager, currentPlayer, game);
	}

	@Test
	public void drawFromPile_drawPileException_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.emptyDeck";
		EasyMock.expect(drawPile.peekTop()).andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalStateException.class,
				game::drawFromPile);

		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(drawPile);
	}

	@Test
	public void drawFromPile_turnManagerException_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createNiceMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createNiceMock(TurnManager.class);

		Card card = EasyMock.createNiceMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(CardType.ATTACK);

		EasyMock.expect(drawPile.peekTop()).andStubReturn(card);
		EasyMock.expect(drawPile.removeTop()).andStubReturn(card);

		Player currentPlayer = EasyMock.createNiceMock(Player.class);

		String expectedMsg = "error.negativeDrawCount";

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				card, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class,
				game::drawFromPile);

		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager, game);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void toggleFaceUp_called_togglesFaceUp(boolean initialFaceUp) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsFaceUp(initialFaceUp);

		game.toggleFaceUp();

		boolean updatedFaceUp = game.getIsFaceUp();

		assertNotEquals(initialFaceUp, updatedFaceUp);
	}

	@Test
	public void toggleSelectedCurrentPlayerCardAt_called_calledPlayerToggle() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int handCardIndex = 0;
		Player currentPlayer = EasyMock.createMock(Player.class);
		currentPlayer.toggleSelectedHandCardAt(handCardIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.toggleSelectedPlayerCardAt(handCardIndex);

		EasyMock.verify(currentPlayer, game);
	}

	@Test
	public void toggleSelectedCurrentPlayerCardAt_indexZero_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int handCardIndex = 0;
		String expectedMsg = "error.invalidHandCardIndex";

		Player currentPlayer = EasyMock.createMock(Player.class);
		currentPlayer.toggleSelectedHandCardAt(handCardIndex);
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.toggleSelectedPlayerCardAt(handCardIndex));

		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(currentPlayer, game);
	}

	@Test
	public void advanceTurn_canEndTurn_advanceTurnAndDeselectCards() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player currentPlayer = EasyMock.createMock(Player.class);
		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		turnManager.incrementTurn();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(currentPlayer);

		EasyMock.replay(game);

		game.advanceTurn();

		EasyMock.verify(turnManager, currentPlayer, game);
	}

	@Test
	public void advanceTurn_cannotEndTurn_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.cannotEndTurn";

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::advanceTurn);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game);
	}

	@ParameterizedTest
	@CsvSource({
			"0",
			"1",
			"2"
	})
	public void getDrawPileSize_called_returnDrawPileMethodCall(int expectedDrawPileSize) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.size()).andReturn(expectedDrawPileSize);

		EasyMock.replay(drawPile);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		int actualDrawPileSize = game.getDrawPileSize();
		assertEquals(expectedDrawPileSize, actualDrawPileSize);

		EasyMock.verify(drawPile);
	}

	@Test
	public void playExplode_emptyDrawPile_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createNiceMock(TurnManager.class);

		Player currentPlayer = EasyMock.createNiceMock(Player.class);

		String expectedMsg = "error.emptyDeck";

		drawPile.removeTop();
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::playExplode);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(drawPile, game);
	}

	@Test
	public void playExplode_called_success() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player currentPlayer = EasyMock.createMock(Player.class);
		Card explodingKitten = EasyMock.createMock(Card.class);

		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		turnManager.incrementTurn();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, explodingKitten);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.playExplode();

		EasyMock.verify(drawPile, turnManager, game, currentPlayer);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard")
	public void isDefusable_noDefuser_returnFalse(
			List<CardType> currentPlayerHandCardTypes, CardType topDiscardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> currentPlayerHandCards = getCardMocksWithTypeExpectations(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = getCardMockWithTypeExpectation(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertFalse(game.isDefusable());

		EasyMock.verify(game);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard() {
		return Stream.of(
				Arguments.of(List.of(), CardType.DEFUSE),
				Arguments.of(List.of(CardType.ATTACK), CardType.ATTACK),
				Arguments.of(List.of(CardType.ATTACK, CardType.SKIP), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.SKIP), CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.SKIP), CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.CLONE), CardType.ATTACK)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithDefuserAndTopDiscardCard")
	public void isDefusable_hasDefuser_returnTrue(
			List<CardType> currentPlayerHandCardTypes, CardType topDiscardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> currentPlayerHandCards = getCardMocksWithTypeExpectations(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = getCardMockWithTypeExpectation(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertTrue(game.isDefusable());

		EasyMock.verify(game);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithDefuserAndTopDiscardCard() {
		return Stream.of(
				Arguments.of(List.of(CardType.DEFUSE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.DEFUSE), CardType.SKIP),
				Arguments.of(List.of(CardType.DEFUSE, CardType.SKIP), CardType.ATTACK),
				Arguments.of(List.of(CardType.DEFUSE, CardType.DEFUSE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.GODCAT), CardType.SKIP),
				Arguments.of(List.of(CardType.GODCAT, CardType.SKIP), CardType.ATTACK),
				Arguments.of(List.of(CardType.GODCAT, CardType.GODCAT), CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.GODCAT), CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT, CardType.DEFUSE), CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.CLONE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.SKIP), CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.CLONE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.CLONE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.DEFUSE), CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.GODCAT), CardType.ATTACK)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard")
	public void playDefuse_noDefuser_failed(List<CardType> currentPlayerHandCardTypes) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int drawPileIndex = 0;
		List<Card> currentPlayerHandCards = getCardMocksWithTypeExpectations(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.playDefuse(drawPileIndex));

		String expectedMsg = "error.currentPlayerNoDefuser";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithDefuserIndex")
	public void playDefuse_hasDefuser_reinsertExplodingKitten(
			List<CardType> currentPlayerHandCardTypes, int defuseIndex) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int drawPileIndex = 0;

		List<Card> currentPlayerHandCards = getCardMocksWithTypeExpectations(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card defuse = currentPlayerHandCards.get(defuseIndex);

		currentPlayer.removeCardFromHand(defuse);
		EasyMock.expectLastCall();

		discardPile.addCard(defuse);
		EasyMock.expectLastCall();

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		drawPile.insertCardAt(explodingKitten, drawPileIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, explodingKitten);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.playDefuse(drawPileIndex);

		EasyMock.verify(game, currentPlayer, drawPile, discardPile);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithDefuserIndex() {
		return Stream.of(
				Arguments.of(List.of(CardType.DEFUSE), 0),
				Arguments.of(List.of(CardType.SKIP, CardType.DEFUSE), 1),
				Arguments.of(List.of(CardType.DEFUSE, CardType.SKIP), 0),
				Arguments.of(List.of(CardType.DEFUSE, CardType.DEFUSE), 0),
				Arguments.of(List.of(CardType.GODCAT), 0),
				Arguments.of(List.of(CardType.SKIP, CardType.GODCAT), 1),
				Arguments.of(List.of(CardType.GODCAT, CardType.SKIP), 0),
				Arguments.of(List.of(CardType.GODCAT, CardType.GODCAT), 0),
				Arguments.of(List.of(CardType.DEFUSE, CardType.GODCAT), 0),
				Arguments.of(List.of(CardType.GODCAT, CardType.DEFUSE), 1)
		);
	}

	@Test
	public void playDefuse_invalidDrawPileIndex_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int drawPileIndex = 0;
		int defuseIndex = 0;
		String expectedMsg = "error.invalidDeckIndex";

		List<CardType> currentPlayerHandCardTypes = List.of(CardType.DEFUSE);
		List<Card> currentPlayerHandCards = getCardMocksWithTypeExpectations(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card defuse = currentPlayerHandCards.get(defuseIndex);

		currentPlayer.removeCardFromHand(defuse);
		EasyMock.expectLastCall();

		discardPile.addCard(defuse);
		EasyMock.expectLastCall();

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		drawPile.insertCardAt(explodingKitten, drawPileIndex);
		EasyMock.expectLastCall().andThrow(
				new IllegalArgumentException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, explodingKitten);

		Game game = createAndSetGameExpectationsWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				game.playDefuse(drawPileIndex));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game, currentPlayer, drawPile, discardPile);
	}

	@Test
	public void applySkip_drawCountOne_TurnAdvances() {
		final int expectedReturnValue = 0;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnValue);
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnValue);
		EasyMock.expect(mockTurnManager.getCurrentPlayerIndex())
				.andReturn(expectedReturnValue);
		mockPlayer1.deselectHandCards();
		mockTurnManager.incrementTurn();

		EasyMock.replay(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@Test
	public void applySkip_drawCountTwo_TurnNotAdvanced() {
		final int expectedDrawCount = 1;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedDrawCount);

		EasyMock.replay(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@Test
	public void applySkip_drawCountThree_TurnNotAdvanced() {
		final int expectedDrawCount = 2;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedDrawCount);

		EasyMock.replay(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@Test
	public void applySkip_lastPlayer_turnWraps() {
		final int expectedDrawCount = 0;
		final int expectedPlayerIndex = 1;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedDrawCount);
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedDrawCount);
		EasyMock.expect(mockTurnManager.getCurrentPlayerIndex())
				.andReturn(expectedPlayerIndex);
		mockPlayer2.deselectHandCards();
		mockTurnManager.incrementTurn();

		EasyMock.replay(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@Test
	public void applySkip_twoPlayers_turnAdvances() {
		final int expectedReturnZero = 0;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnZero);
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnZero);
		EasyMock.expect(mockTurnManager.getCurrentPlayerIndex())
				.andReturn(expectedReturnZero);
		mockPlayer1.deselectHandCards();
		mockTurnManager.incrementTurn();

		EasyMock.replay(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@Test
	public void applySkip_fourPlayers_turnAdvances() {
		final int expectedReturnZero = 0;
		Player mockPlayer1 = EasyMock.createMock(Player.class);
		Player mockPlayer2 = EasyMock.createMock(Player.class);
		Player mockPlayer3 = EasyMock.createMock(Player.class);
		Player mockPlayer4 = EasyMock.createMock(Player.class);
		List<Player> players = new ArrayList<>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);
		players.add(mockPlayer3);
		players.add(mockPlayer4);

		Deck mockDrawPile = EasyMock.createMock(Deck.class);
		Deck mockDiscardPile = EasyMock.createMock(Deck.class);
		TurnManager mockTurnManager = EasyMock.createMock(TurnManager.class);

		mockTurnManager.decrementDrawCount();
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnZero);
		EasyMock.expect(mockTurnManager.getDrawCount()).andReturn(expectedReturnZero);
		EasyMock.expect(mockTurnManager.getCurrentPlayerIndex())
				.andReturn(expectedReturnZero);
		mockPlayer1.deselectHandCards();
		mockTurnManager.incrementTurn();

		EasyMock.replay(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4,
				mockDrawPile, mockDiscardPile, mockTurnManager);

		Game game = new Game(players, mockDrawPile, mockDiscardPile, mockTurnManager);
		game.setIsGameOngoing(true);

		game.applySkip();

		EasyMock.verify(mockPlayer1, mockPlayer2, mockPlayer3, mockPlayer4,
				mockDrawPile, mockDiscardPile, mockTurnManager);
	}

	@ParameterizedTest
	@MethodSource("provideCardIds")
	public void getSeeTheFutureCardIds_called_returnTopDrawPileCards(List<String> expectedCardIds) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> cards = getCardMocksWithIdExpectations(expectedCardIds);

		EasyMock.expect(drawPile.peekTopNCards(SEE_THE_FUTURE_PEEK_COUNT))
				.andReturn(cards);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		List<String> actualCardIds = game.getSeeTheFutureCardIds();

		assertEquals(expectedCardIds, actualCardIds);

		EasyMock.verify(drawPile);
	}

	private static Stream<Arguments> provideCardIds() {
		return Stream.of(
				Arguments.of(List.of()),
				Arguments.of(List.of("SKIP_1")),
				Arguments.of(List.of("SKIP_1", "SKIP_2")),
				Arguments.of(List.of("SKIP_1", "ATTACK_1")),
				Arguments.of(List.of("SKIP_1", "SKIP_1"))
		);
	}

	@Test
	public void applyGodcat_invalidGodcat_throwsException() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.applyGodcat(CardType.GODCAT));

		String expectedMsg = "error.cannotPlaySelectedCards";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
	}

	@ParameterizedTest
	@MethodSource("provideValidPlaysAndMethods")
	public void applyGodcate_validCardType_correctApplyCalled(
			CardType cardType, String applyMethodName,
			Consumer<Game> applyMethod) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod(applyMethodName)
				.createMock();

		applyMethod.accept(game);
		EasyMock.expectLastCall();

		EasyMock.replay(game);

		game.applyGodcat(cardType);

		EasyMock.verify(game);
	}

	@ParameterizedTest
	@MethodSource("provideValidCardTypesForGodcatWithoutApplyMethod")
	public void applyGodcat_validPlayWithoutApplyMethod_noApplyCalled(
			CardType cardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.applyGodcat(cardType);
	}

	private static Stream<Arguments> provideValidCardTypesForGodcatWithoutApplyMethod() {
		return Stream.of(
				Arguments.of(CardType.SEE_THE_FUTURE),
				Arguments.of(CardType.TARGETED_ATTACK)
		);
	}

	private static List<Card> getCardMocksWithTypeExpectations(List<CardType> cardTypes) {
		List<Card> selectedCards = new ArrayList<>();

		for (CardType cardType : cardTypes) {
			Card card = getCardMockWithTypeExpectation(cardType);

			selectedCards.add(card);
		}

		return selectedCards;
	}

	private static Card getCardMockWithTypeExpectation(CardType cardType) {
		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(cardType);
		EasyMock.replay(card);

		return card;
	}

	private static List<Card> getCardMocksWithIdExpectations(List<String> cardIds) {
		List<Card> selectedCards = new ArrayList<>();

		for (String cardId : cardIds) {
			Card card = getCardMockWithIdExpectation(cardId);

			selectedCards.add(card);
		}

		return selectedCards;
	}

	private static Card getCardMockWithIdExpectation(String cardId) {
		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getId()).andStubReturn(cardId);
		EasyMock.replay(card);

		return card;
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