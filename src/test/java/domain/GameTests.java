package domain;

import org.easymock.EasyMock;

import org.easymock.IArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
		assertFalse(game.getCanPlay());
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
					CardType.DEFUSE, GameConstants.NUM_DEFUSES_IN_GAME - i));
			EasyMock.expectLastCall();
		}

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (Player player : players) {
			for (int i = 0; i < GameConstants.STARTING_HAND_SIZE - 1; i++) {
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
			drawPile.addCardToTop(mockSpecificCard(CardType.EXPLODING_KITTEN, i));
			EasyMock.expectLastCall();
		}

		drawPile.shuffle();
		EasyMock.expectLastCall();

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("changeCurrentPlayerIndex")
				.createMock();

		game.changeCurrentPlayerIndex(GameConstants.STARTING_PLAYER_INDEX);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, game);

		game.startGame();

		assertTrue(game.getIsGameOngoing());
		assertTrue(game.getCanPlay());

		EasyMock.verify(drawPile, game);
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

		assertEquals(GameConstants.STARTING_PLAYER_INDEX, actualStartingPlayerIndex);
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

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		List<String> actualHandIds = game.getCurrentPlayerHandIds();

		assertEquals(expectedHandIds, actualHandIds);

		EasyMock.verify(currentPlayer, game);
	}

	@ParameterizedTest
	@MethodSource("provideInvalidCardSelections")
	public void canPlaySelected_invalidCards_returnFalse(List<CardType> selectedCardTypes) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> selectedCards = mockCardsOfTypes(selectedCardTypes);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.setCanPlay(true);

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

	@Test
	public void canPlaySelected_cannotPlay_returnFalse() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		game.setCanPlay(false);

		assertFalse(game.canPlaySelected());
	}

	@ParameterizedTest
	@MethodSource("provideValidCardSelections")
	public void canPlaySelected_validCards_returnTrue(List<CardType> selectedCardTypes) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> selectedCards = mockCardsOfTypes(selectedCardTypes);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.setCanPlay(true);

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

		CardType expectedCardType = CardType.DEFUSE;
		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(expectedCardType);

		List<Card> selectedCards = List.of(card);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayerAndCanPlaySelected(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();
		assertEquals(expectedCardType, actualCardType);

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

			discardPile.addCardToTop(selectedCard);
			EasyMock.expectLastCall();
		}
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

		Game game = mockGameWithGetCurrentPlayerAndCanPlaySelected(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

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

		EasyMock.expect(game.canPlaySelected()).andStubReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);

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
				Arguments.of(CardType.SWAP_TOP_AND_BOTTOM, "applySwapTopAndBottom",
						(Consumer<Game>) Game::applySwapTopAndBottom),
				Arguments.of(
						CardType.WINNER_WINNER_CATNIP_DINNER,
						"applyWinnerWinnerCatnipDinner",
						(Consumer<Game>) Game::applyWinnerWinnerCatnipDinner
				),
				Arguments.of(CardType.DOUBLE_UP, "applyDoubleUp",
						(Consumer<Game>) Game::applyDoubleUp),
				Arguments.of(CardType.MILD_SHUFFLE, "applyMildShuffle",
						(Consumer<Game>) Game::applyMildShuffle)
		);
	}

	@Test
	public void playSelectedCards_clonePlayed_returnsClonedCardType() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card cloneCard = EasyMock.createMock(Card.class);
		EasyMock.expect(cloneCard.getType()).andStubReturn(CardType.CLONE);

		Card clonedCard = mockCardOfType(CardType.ATTACK);

		List<Card> selectedCards = List.of(cloneCard);
		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getSelectedCards()).andReturn(selectedCards);

		EasyMock.expect(discardPile.peekTop()).andReturn(clonedCard);

		setMoveCardToDiscardExpectations(selectedCards, discardPile, currentPlayer);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod("applyClone", Card.class)
				.createMock();

		EasyMock.expect(game.canPlaySelected()).andStubReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);
		EasyMock.expect(game.applyClone(clonedCard)).andReturn(CardType.ATTACK);

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();

		assertEquals(CardType.ATTACK, actualCardType);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, currentPlayer, game);
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

		Game game = mockGameWithGetCurrentPlayerAndCanPlaySelected(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

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
				Arguments.of(CardType.DRAW_FROM_THE_BOTTOM),
				Arguments.of(CardType.TARGETED_ATTACK)
		);
	}

	@Test
	public void applySuperSkip_called_endTurn() {
		List<Player> players = EasyMock.createMock(List.class);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.setDrawCount(GameConstants.NUM_DRAW_COUNT_AFTER_SUPER_SKIP);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("endTurn")
				.createMock();

		game.endTurn();
		EasyMock.expectLastCall();

		EasyMock.replay(game);

		game.setIsGameOngoing(true);

		game.applySuperSkip();

		EasyMock.verify(turnManager, game);
	}

	@Test
	public void applySuperSkip_endTurnThrows_failed() {
		List<Player> players = EasyMock.createMock(List.class);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.setDrawCount(GameConstants.NUM_DRAW_COUNT_AFTER_SUPER_SKIP);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("endTurn")
				.createMock();

		String expectedMsg = "error.cannotEndTurn";
		game.endTurn();
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(game);

		game.setIsGameOngoing(true);

		Exception exception = assertThrows(IllegalStateException.class,
				game::applySuperSkip);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager, game);
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

		Game game = mockGameWithGetCurrentPlayerAndCanPlaySelected(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(game);

		CardType actualCardType = game.playSelectedCards();

		assertEquals(CardType.GODCAT, actualCardType);

		Object[] selectedCardsArray = selectedCards.toArray();
		EasyMock.verify(selectedCardsArray);
		EasyMock.verify(discardPile, currentPlayer, game);
	}

	@Test
	public void getTopDiscardId_emptyDiscardPile_returnEmptyString() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(discardPile.isEmpty()).andReturn(true);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		String expectedTopDiscardId = "global.empty";
		String actualTopDiscardId = game.getTopDiscardId();

		assertEquals(expectedTopDiscardId, actualTopDiscardId);

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

		EasyMock.expect(discardPile.isEmpty()).andReturn(false);
		EasyMock.expect(discardPile.peekTop()).andReturn(topCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, topCard);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		String actualId = game.getTopDiscardId();

		assertEquals(expectedId, actualId);

		EasyMock.verify(discardPile);
	}

	@ParameterizedTest
	@CsvSource({
			"1",
			"2"
	})
	public void canEndTurn_positiveDrawCount_returnFalse(int drawCount) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(drawCount);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.canEndTurn());

		EasyMock.verify(turnManager);
	}

	@Test
	public void canEndTurn_drawCountZero_returnTrue() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(0);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

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
	public void getCanDraw_canDrawFalse_returnTrue(int drawCount) {
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

	@ParameterizedTest
	@CsvSource({
			"false, false",
			"true, false"
	})
	public void getCanPlay_called_returnFalse(boolean isGameOngoing, boolean canPlay) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsGameOngoing(isGameOngoing);
		game.setCanPlay(canPlay);

		assertFalse(game.getCanPlay());
	}

	@Test
	public void getCanPlay_called_returnTrue() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsGameOngoing(true);
		game.setCanPlay(true);

		assertTrue(game.getCanPlay());
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

		Set<Integer> aliveIndices = Set.of(0);
		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getAliveIndices")
				.createMock();

		EasyMock.expect(game.getAliveIndices()).andReturn(aliveIndices);

		EasyMock.replay(game);

		game.changeCurrentPlayerIndex(newPlayerIndex);

		assertFalse(game.getIsFaceUp());
		EasyMock.verify(turnManager, game);
	}

	@Test
	public void changeCurrentPlayerIndex_alivePlayerIndex_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int newPlayerIndex = 0;

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Set<Integer> aliveIndices = Set.of(1);
		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getAliveIndices")
				.createMock();

		EasyMock.expect(game.getAliveIndices()).andReturn(aliveIndices);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.changeCurrentPlayerIndex(newPlayerIndex));

		String expectedMsg = "error.playerIsDead";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
		EasyMock.verify(game);
	}

	@Test
	public void changeCurrentPlayerIndex_called_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int newPlayerIndex = 1;
		Set<Integer> aliveIndices = Set.of(0, 1);
		String expectedMsg = "error.invalidPlayerIndex";

		turnManager.setCurrentPlayerIndex(newPlayerIndex);
		EasyMock.expectLastCall().andThrow(
				new IllegalArgumentException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getAliveIndices")
				.createMock();

		EasyMock.expect(game.getAliveIndices()).andReturn(aliveIndices);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				game.changeCurrentPlayerIndex(newPlayerIndex));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager, game);
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

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromPile();
		assertEquals(expectedCard, actualCard);

		assertFalse(game.getCanPlay());
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

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromPile();
		assertEquals(expectedCard, actualCard);

		assertFalse(game.getCanPlay());
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

		Game game = mockGameWithGetCurrentPlayer(
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

		Game game = mockGameWithGetCurrentPlayer(
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

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.toggleSelectedPlayerCardAt(handCardIndex));

		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(currentPlayer, game);
	}

	@Test
	public void endTurn_canEndTurnNoWinnerWinner_endTurnAndDeselectCards() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Set<Integer> aliveIndices = EasyMock.createMock(Set.class);

		Player currentPlayer = EasyMock.createMock(Player.class);
		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		turnManager.incrementTurn(aliveIndices);
		EasyMock.expectLastCall();

		turnManager.incrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(aliveIndices, currentPlayer,
				players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod("getAliveIndices")
				.addMockedMethod("reachedWinnerWinnerCondition")
				.createMock();

		EasyMock.expect(game.reachedWinnerWinnerCondition()).andReturn(false);
		EasyMock.expect(game.getAliveIndices()).andReturn(aliveIndices);
		EasyMock.expect(game.canEndTurn()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(currentPlayer);

		EasyMock.replay(game);

		game.setIsFaceUp(false);
		game.setCanPlay(true);
		game.setIsGameOngoing(true);
		game.endTurn();

		assertTrue(game.getCanPlay());
		assertFalse(game.getIsFaceUp());
		EasyMock.verify(turnManager, currentPlayer, game);
	}

	@Test
	public void endTurn_winnerAtIndexZero_endGame() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		player1.deselectHandCards();
		EasyMock.expectLastCall();

		player2.eliminate();
		EasyMock.expectLastCall();

		EasyMock.replay(player1, player2, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod("reachedWinnerWinnerCondition")
				.createMock();

		EasyMock.expect(game.reachedWinnerWinnerCondition()).andReturn(true);
		EasyMock.expect(game.canEndTurn()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player1).atLeastOnce();

		EasyMock.replay(game);

		game.setIsGameOngoing(true);
		game.setCanPlay(false);
		game.setIsFaceUp(true);
		game.endTurn();

		assertFalse(game.getCanPlay());
		assertTrue(game.getIsFaceUp());
		assertFalse(game.getIsGameOngoing());
		EasyMock.verify(player1, player2, game);
	}

	@Test
	public void endTurn_winnerAtLastIndex_endGame() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Player player3 = EasyMock.createMock(Player.class);
		Player player4 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2, player3, player4);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		for (int i = 0; i < players.size() - 1; i++) {
			Player player = players.get(i);
			player.eliminate();
			EasyMock.expectLastCall();

			EasyMock.replay(player);
		}

		player4.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(player4, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("getCurrentPlayer")
				.addMockedMethod("reachedWinnerWinnerCondition")
				.createMock();

		EasyMock.expect(game.reachedWinnerWinnerCondition()).andReturn(true);
		EasyMock.expect(game.canEndTurn()).andReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andReturn(player4).atLeastOnce();

		EasyMock.replay(game);

		game.setIsGameOngoing(true);
		game.setCanPlay(false);
		game.setIsFaceUp(true);
		game.endTurn();

		assertFalse(game.getCanPlay());
		assertTrue(game.getIsFaceUp());
		assertFalse(game.getIsGameOngoing());

		players.forEach(EasyMock::verify);
		EasyMock.verify(game);
	}

	@Test
	public void endTurn_cannotEndTurn_failed() {
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

		Exception exception = assertThrows(IllegalStateException.class, game::endTurn);

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

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalStateException.class, game::playExplode);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(drawPile, game);
	}

	@ParameterizedTest
	@MethodSource("provideExplodeGameEndsConditions")
	public void playExplode_twoAlivePlayers_oneWins(
			int numPlayers, int currentPlayerIndex, Set<Integer> expectedAliveIndices) {

		List<Player> players = mockPlayersWithExplodingExpectations(
				numPlayers, currentPlayerIndex, expectedAliveIndices);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getCurrentPlayerIndex())
				.andStubReturn(currentPlayerIndex);

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		EasyMock.replay(drawPile, discardPile, turnManager,
				explodingKitten);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsGameOngoing(true);

		game.playExplode();

		Set<Integer> actualAliveIndices = game.getAliveIndices();
		assertEquals(expectedAliveIndices, actualAliveIndices);

		assertFalse(game.getIsGameOngoing());

		players.forEach(EasyMock::verify);
		EasyMock.verify(drawPile);
	}

	private static Stream<Arguments> provideExplodeGameEndsConditions() {
		return Stream.of(
				Arguments.of(2, 0, Set.of(1)),
				Arguments.of(GameConstants.MAX_PLAYERS - 1, 0, Set.of(2))
		);
	}

	@ParameterizedTest
	@MethodSource("provideExplodeGameContinuesConditions")
	public void playExplode_threeAlive_gameContinues(
			int numPlayers, int currentPlayerIndex, Set<Integer> expectedAliveIndices) {

		List<Player> players = mockPlayersWithExplodingExpectations(
				numPlayers, currentPlayerIndex, expectedAliveIndices);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getCurrentPlayerIndex())
				.andStubReturn(currentPlayerIndex);

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		turnManager.incrementTurn(expectedAliveIndices);
		EasyMock.expectLastCall();

		turnManager.incrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(drawPile, discardPile, turnManager,
				explodingKitten);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.setIsGameOngoing(true);

		game.playExplode();

		Set<Integer> actualAliveIndices = game.getAliveIndices();
		assertEquals(expectedAliveIndices, actualAliveIndices);

		assertTrue(game.getIsGameOngoing());

		players.forEach(EasyMock::verify);
		EasyMock.verify(drawPile, turnManager);
	}

	private static Stream<Arguments> provideExplodeGameContinuesConditions() {
		return Stream.of(
				Arguments.of(GameConstants.MAX_PLAYERS - 1, 2, Set.of(0, 1)),
				Arguments.of(
						GameConstants.MAX_PLAYERS,
						GameConstants.MAX_PLAYER_INDEX,
						Set.of(1, 2))
		);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard")
	public void isDefusable_noDefuser_returnFalse(
			List<CardType> currentPlayerHandCardTypes, CardType topDiscardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> currentPlayerHandCards = mockCardsOfTypes(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = mockCardOfType(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertFalse(game.isDefusable());

		EasyMock.verify(game);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard() {
		return Stream.of(
				Arguments.of(List.of(),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.ATTACK),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.ATTACK, CardType.SKIP),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.SKIP),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.SKIP),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.CLONE),
						CardType.ATTACK)
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

		List<Card> currentPlayerHandCards = mockCardsOfTypes(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = mockCardOfType(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		assertTrue(game.isDefusable());

		EasyMock.verify(game);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithDefuserAndTopDiscardCard() {
		return Stream.of(
				Arguments.of(List.of(CardType.DEFUSE),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.DEFUSE),
						CardType.SKIP),
				Arguments.of(List.of(CardType.DEFUSE, CardType.SKIP),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.DEFUSE, CardType.DEFUSE),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.GODCAT),
						CardType.SKIP),
				Arguments.of(List.of(CardType.GODCAT, CardType.SKIP),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.GODCAT, CardType.GODCAT),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.GODCAT),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT, CardType.DEFUSE),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE), CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.CLONE),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.SKIP),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.CLONE),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.CLONE),
						CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.DEFUSE),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.CLONE, CardType.GODCAT),
						CardType.ATTACK),
				Arguments.of(List.of(CardType.GODCAT, CardType.CLONE),
						CardType.DEFUSE),
				Arguments.of(
						List.of(
								CardType.GODCAT,
								CardType.CLONE,
								CardType.DEFUSE),
						CardType.DEFUSE)
		);
	}

	@ParameterizedTest
	@MethodSource("provideCurrentPlayerHandWithNoDefuserAndTopDiscardCard")
	public void playDefuse_noDefuser_failed(
			List<CardType> currentPlayerHandCardTypes, CardType topDiscardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int drawPileIndex = 0;
		List<Card> currentPlayerHandCards = mockCardsOfTypes(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = mockCardOfType(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
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
			List<CardType> currentPlayerHandCardTypes, int defuseIndex,
			CardType topDiscardType) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int drawPileIndex = 0;

		List<Card> currentPlayerHandCards = mockCardsOfTypes(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card topDiscardCard = mockCardOfType(topDiscardType);
		EasyMock.expect(discardPile.peekTop()).andStubReturn(topDiscardCard);

		Card defuse = currentPlayerHandCards.get(defuseIndex);

		currentPlayer.removeCardFromHand(defuse);
		EasyMock.expectLastCall();

		discardPile.addCardToTop(defuse);
		EasyMock.expectLastCall();

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		drawPile.insertCardAt(explodingKitten, drawPileIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, explodingKitten);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		game.playDefuse(drawPileIndex);

		EasyMock.verify(game, currentPlayer, drawPile, discardPile);
	}

	private static Stream<Arguments> provideCurrentPlayerHandWithDefuserIndex() {
		return Stream.of(
				Arguments.of(
						List.of(CardType.DEFUSE),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.DEFUSE),
						1, CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.SKIP),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.DEFUSE),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.GODCAT),
						1, CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT, CardType.SKIP),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT, CardType.GODCAT),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.GODCAT),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.GODCAT, CardType.DEFUSE),
						1, CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.SKIP, CardType.CLONE),
						1, CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.SKIP),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.CLONE),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.DEFUSE, CardType.CLONE),
						0, CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.DEFUSE),
						1, CardType.DEFUSE),
				Arguments.of(List.of(CardType.CLONE, CardType.GODCAT),
						1, CardType.ATTACK),
				Arguments.of(
						List.of(CardType.GODCAT, CardType.CLONE),
						1, CardType.DEFUSE),
				Arguments.of(List.of(
								CardType.GODCAT,
								CardType.CLONE,
								CardType.DEFUSE),
						2, CardType.DEFUSE)
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
		List<Card> currentPlayerHandCards = mockCardsOfTypes(
				currentPlayerHandCardTypes);

		Player currentPlayer = EasyMock.createMock(Player.class);
		EasyMock.expect(currentPlayer.getHand()).andStubReturn(currentPlayerHandCards);

		Card defuse = currentPlayerHandCards.get(defuseIndex);

		currentPlayer.removeCardFromHand(defuse);
		EasyMock.expectLastCall();

		discardPile.addCardToTop(defuse);
		EasyMock.expectLastCall();

		Card explodingKitten = EasyMock.createMock(Card.class);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten);

		drawPile.insertCardAt(explodingKitten, drawPileIndex);
		EasyMock.expectLastCall().andThrow(
				new IllegalArgumentException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, explodingKitten);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				game.playDefuse(drawPileIndex));

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(game, currentPlayer, drawPile, discardPile);
	}

	@Test
	public void applyShuffle_called_shufflesDrawPile() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		drawPile.shuffle();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applyShuffle();

		EasyMock.verify(drawPile);
	}

	@Test
	public void applySkip_cannotEndTurn_decrementDrawCount() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);

		EasyMock.replay(players, drawPile, discardPile, turnManager, game);

		game.applySkip();

		EasyMock.verify(turnManager, game);
	}

	@Test
	public void applySkip_canEndTurn_decrementDrawCountAndEndTurn() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("endTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		game.endTurn();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, game);

		game.applySkip();

		EasyMock.verify(turnManager, game);
	}

	@Test
	public void applySkip_canEndTurnThrows_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.addMockedMethod("endTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);

		String expectedMsg = "error.cannotEndTurn";
		game.endTurn();
		EasyMock.expectLastCall().andThrow(
				new IllegalStateException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager, game);

		Exception exception = assertThrows(IllegalStateException.class, game::applySkip);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager, game);
	}

	@ParameterizedTest
	@MethodSource("provideCardIds")
	public void getSeeTheFutureCardIds_called_returnTopDrawPileCards(
			List<String> expectedCardIds) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		List<Card> cards = mockCardsWithIds(expectedCardIds);

		EasyMock.expect(
				drawPile.peekTopNCards(GameConstants.SEE_THE_FUTURE_PEEK_COUNT))
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
	public void applyCatomicBomb_emptyDeck_remainsEmpty() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("endTurn")
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		game.endTurn();
		EasyMock.expectLastCall();
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@Test
	public void applyCatomicBomb_noExplodingKittens_deckUnchanged() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card skip1 = mockCardOfType(CardType.SKIP);
		Card attack1 = mockCardOfType(CardType.ATTACK);
		Card shuffle1 = mockCardOfType(CardType.SHUFFLE);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(skip1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(attack1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(shuffle1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		drawPile.addCardToBottom(skip1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(attack1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(shuffle1);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@Test
	public void applyCatomicBomb_oneExplodingKittenAlreadyOnTop_deckUnchanged() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card explodingKitten1 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card attack1 = mockCardOfType(CardType.ATTACK);
		Card shuffle1 = mockCardOfType(CardType.SHUFFLE);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(attack1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(shuffle1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		drawPile.addCardToBottom(attack1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(shuffle1);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten1);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("endTurn")
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		game.endTurn();
		EasyMock.expectLastCall();
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@Test
	public void applyCatomicBomb_oneExplodingKittenInMiddle_movedToTop() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card skip1 = mockCardOfType(CardType.SKIP);
		Card attack1 = mockCardOfType(CardType.ATTACK);
		Card explodingKitten1 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card shuffle1 = mockCardOfType(CardType.SHUFFLE);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(skip1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(attack1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(shuffle1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		drawPile.addCardToBottom(skip1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(attack1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(shuffle1);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten1);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();
		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@Test
	public void applyCatomicBomb_multipleExplodingKittens_allMovedToTop() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card skip1 = mockCardOfType(CardType.SKIP);
		Card explodingKitten1 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card attack1 = mockCardOfType(CardType.ATTACK);
		Card explodingKitten2 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card shuffle1 = mockCardOfType(CardType.SHUFFLE);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(skip1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(attack1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten2);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(shuffle1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		drawPile.addCardToBottom(skip1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(attack1);
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(shuffle1);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten1);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten2);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("endTurn")
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(true);
		game.endTurn();
		EasyMock.expectLastCall();
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@Test
	public void applyCatomicBomb_allExplodingKittens_deckOrderUnchanged() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card explodingKitten1 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card explodingKitten2 = mockCardOfType(CardType.EXPLODING_KITTEN);
		Card explodingKitten3 = mockCardOfType(CardType.EXPLODING_KITTEN);

		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten1);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten2);
		EasyMock.expect(drawPile.isEmpty()).andReturn(false);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten3);
		EasyMock.expect(drawPile.isEmpty()).andReturn(true);

		drawPile.addCardToTop(explodingKitten1);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten2);
		EasyMock.expectLastCall();
		drawPile.addCardToTop(explodingKitten3);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canEndTurn")
				.createMock();

		EasyMock.expect(game.canEndTurn()).andReturn(false);
		EasyMock.replay(game);
		game.applyCatomicBomb();

		EasyMock.verify(drawPile, turnManager, game);
	}

	@ParameterizedTest
	@MethodSource("provideInvalidGodcatCardTypes")
	public void applyGodcat_invalidCardType_throwsException(CardType cardType) {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalArgumentException.class, () ->
				game.applyGodcat(cardType));

		String expectedMsg = "error.cannotPlaySelectedCards";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
	}

	private static Stream<Arguments> provideInvalidGodcatCardTypes() {
		return Stream.of(
				Arguments.of(CardType.GODCAT),
				Arguments.of(CardType.EXPLODING_KITTEN),
				Arguments.of(CardType.DEFUSE)
		);
	}

	@ParameterizedTest
	@MethodSource("provideValidPlaysAndMethods")
	public void applyGodcat_validCardType_correctApplyCalled(
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

	@Test
	public void applyGodcat_cloneCardType_callsApplyClone() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card clonedCard = mockCardOfType(CardType.ATTACK);
		EasyMock.expect(discardPile.peekTop()).andReturn(clonedCard);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("applyClone", Card.class)
				.createMock();

		EasyMock.expect(game.applyClone(clonedCard)).andReturn(CardType.ATTACK);

		EasyMock.replay(game);

		game.applyGodcat(CardType.CLONE);

		EasyMock.verify(discardPile, game);
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
				Arguments.of(CardType.TARGETED_ATTACK),
				Arguments.of(CardType.RECYCLE)
		);
	}

	@Test
	public void applySwapTopAndBottom_emptyDeck_remainsEmpty() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.size()).andReturn(0);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applySwapTopAndBottom();

		EasyMock.verify(drawPile);
	}

	@Test
	public void applySwapTopAndBottom_oneCard_deckUnchanged() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(drawPile.size()).andReturn(1);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applySwapTopAndBottom();

		EasyMock.verify(drawPile);
	}

	@Test
	public void applySwapTopAndBottom_moreThanOneCard_swapped() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card1 = new Card("SKIP_1", CardType.SKIP);
		Card card4 = new Card("ATTACK_1", CardType.ATTACK);

		final int drawPileSize = 4;
		EasyMock.expect(drawPile.size()).andReturn(drawPileSize);
		EasyMock.expect(drawPile.removeTop()).andReturn(card1);
		EasyMock.expect(drawPile.removeBottom()).andReturn(card4);

		drawPile.addCardToTop(mockSpecificCard(CardType.ATTACK, 1));
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(mockSpecificCard(CardType.SKIP, 1));
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applySwapTopAndBottom();

		EasyMock.verify(drawPile);
	}

	@Test
	public void applySwapTopAndBottom_sameType_swapped() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card explodingKitten1 = new Card("EXPLODINGKITTEN_1", CardType.EXPLODING_KITTEN);
		Card explodingKitten2 = new Card("EXPLODINGKITTEN_2", CardType.EXPLODING_KITTEN);

		final int drawPileSize = 4;
		EasyMock.expect(drawPile.size()).andReturn(drawPileSize);
		EasyMock.expect(drawPile.removeTop()).andReturn(explodingKitten1);
		EasyMock.expect(drawPile.removeBottom()).andReturn(explodingKitten2);

		drawPile.addCardToTop(mockSpecificCard(CardType.EXPLODING_KITTEN, 2));
		EasyMock.expectLastCall();
		drawPile.addCardToBottom(mockSpecificCard(CardType.EXPLODING_KITTEN, 1));
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applySwapTopAndBottom();

		EasyMock.verify(drawPile);
	}

	@Test
	public void drawFromTheBottom_nonExplodingCard_returnsDrawnCard() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card expectedCard = EasyMock.createMock(Card.class);
		EasyMock.expect(expectedCard.getType()).andStubReturn(CardType.ATTACK);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(drawPile.peekBottom()).andReturn(expectedCard);
		EasyMock.expect(drawPile.removeBottom()).andReturn(expectedCard);

		currentPlayer.addCardToHand(expectedCard);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				expectedCard, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromTheBottom();

		assertEquals(expectedCard, actualCard);
		assertFalse(game.getCanPlay());

		EasyMock.verify(drawPile, turnManager, currentPlayer, game);
	}

	@Test
	public void drawFromTheBottom_explodingCard_returnsExplodingCard() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card expectedCard = EasyMock.createMock(Card.class);
		EasyMock.expect(expectedCard.getType())
				.andStubReturn(CardType.EXPLODING_KITTEN);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(drawPile.peekBottom()).andReturn(expectedCard);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				expectedCard, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawFromTheBottom();

		assertEquals(expectedCard, actualCard);
		assertFalse(game.getCanPlay());

		EasyMock.verify(drawPile, turnManager, currentPlayer, game);
	}

	@ParameterizedTest
	@MethodSource("applyTargetedAttackArgs")
	public void applyTargetedAttack_validTargets_successfullyCalled(
			int currentPlayerIndex, int targetPlayerIndex, int[] loopSequence) {

		List<Player> players = EasyMock.createMock(List.class);
		Player currentPlayer = EasyMock.createMock(Player.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Set<Integer> aliveIndices = EasyMock.createMock(Set.class);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("addAttackDrawCount")
				.addMockedMethod("getAliveIndices")
				.createMock();

		EasyMock.expect(game.getAliveIndices()).andStubReturn(aliveIndices);
		EasyMock.expect(turnManager.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(players.get(currentPlayerIndex)).andReturn(currentPlayer);

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		for (int i = 0; i < loopSequence.length - 1; i++) {
			EasyMock.expect(turnManager.getCurrentPlayerIndex())
					.andReturn(loopSequence[i]);
			turnManager.incrementTurn(aliveIndices);
			EasyMock.expectLastCall();
		}

		EasyMock.expect(turnManager.getCurrentPlayerIndex())
				.andReturn(loopSequence[loopSequence.length - 1]);

		game.addAttackDrawCount();
		EasyMock.expectLastCall();

		EasyMock.replay(players, currentPlayer, drawPile, discardPile, turnManager, game,
				aliveIndices);

		game.applyTargetedAttack(targetPlayerIndex);

		EasyMock.verify(players, currentPlayer, drawPile, discardPile, turnManager, game);
	}

	private static Stream<Arguments> applyTargetedAttackArgs() {
		return Stream.of(
				Arguments.of(0, 1, new int[]{0, 1}),
				Arguments.of(1, 0, new int[]{1, 0}),
				Arguments.of(0, GameConstants.MAX_PLAYER_INDEX,
						new int[]{0, 1, 2, GameConstants.MAX_PLAYER_INDEX}),
				Arguments.of(GameConstants.MAX_PLAYER_INDEX, 0,
						new int[]{GameConstants.MAX_PLAYER_INDEX, 0})
		);
	}

	@Test
	public void addAttackDrawCount_drawCountZero_SetTwo() {
		final int expectedDrawCount = 1;
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(expectedDrawCount);

		turnManager.setDrawCount(GameConstants.ATTACK_DRAW_COUNT);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.addAttackDrawCount();

		EasyMock.verify(players, drawPile, discardPile, turnManager);
	}

	@Test
	public void addAttackDrawCount_drawCountTwo_addsTwo() {
		final int expectedDrawCount = 2;
		final int finalDrawCount = 4;
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getDrawCount()).andReturn(expectedDrawCount).times(2);

		turnManager.setDrawCount(finalDrawCount);
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.addAttackDrawCount();

		EasyMock.verify(players, drawPile, discardPile, turnManager);
	}

	static Stream<Arguments> applyRagebaitArgs() {
		return Stream.of(
				Arguments.of(0, 1),
				Arguments.of(0, GameConstants.MAX_PLAYER_INDEX),
				Arguments.of(GameConstants.MAX_PLAYER_INDEX, 0)
		);
	}

	@ParameterizedTest
	@MethodSource("applyRagebaitArgs")
	public void applyRagebait_validTargets_swapsHands(
			int currentPlayerIndex, int targetPlayerIndex) {

		List<Player> players = EasyMock.createMock(List.class);
		Player currentPlayer = EasyMock.createMock(Player.class);
		Player targetPlayer = EasyMock.createMock(Player.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(turnManager.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(players.get(currentPlayerIndex)).andReturn(currentPlayer);
		EasyMock.expect(players.get(targetPlayerIndex)).andReturn(targetPlayer);

		currentPlayer.swapHandWith(targetPlayer);
		EasyMock.expectLastCall();

		EasyMock.replay(players, currentPlayer,
				targetPlayer, drawPile,
				discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);
		game.applyRagebait(targetPlayerIndex);

		EasyMock.verify(players, currentPlayer,
				targetPlayer, drawPile,
				discardPile, turnManager);
	}

	@Test
	public void reachedWinnerWinnerCondition_notActivated_returnFalse() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(currentPlayer.isWinnerWinnerActivated()).andReturn(false);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, game);

		assertFalse(game.reachedWinnerWinnerCondition());

		EasyMock.verify(currentPlayer, game);
	}

	@ParameterizedTest
	@CsvSource({
			"true,  1, 0",
			"true,  1, 2"
	})
	public void reachedWinnerWinnerCondition_wrongNumberOfRounds_returnFalse(
			boolean isActivated, int activatedRound, int buffer) {

		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(currentPlayer.isWinnerWinnerActivated()).andReturn(
				isActivated
		);
		EasyMock.expect(currentPlayer.getWinnerWinnerActivatedRound()).andReturn(
				activatedRound
		);

		EasyMock.expect(turnManager.getRoundCount()).andReturn(
				GameConstants.WINNER_WINNER_REQUIRED_ROUNDS + buffer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, game);

		assertFalse(game.reachedWinnerWinnerCondition());

		EasyMock.verify(currentPlayer, turnManager, game);
	}

	@Test
	public void reachedWinnerWinnerCondition_reachedRequirement_returnTrue() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Player currentPlayer = EasyMock.createMock(Player.class);

		EasyMock.expect(currentPlayer.isWinnerWinnerActivated()).andReturn(true);
		EasyMock.expect(currentPlayer.getWinnerWinnerActivatedRound()).andReturn(1);

		EasyMock.expect(turnManager.getRoundCount()).andReturn(
				GameConstants.WINNER_WINNER_REQUIRED_ROUNDS + 1);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager,
				currentPlayer, game);

		assertTrue(game.reachedWinnerWinnerCondition());

		EasyMock.verify(currentPlayer, turnManager, game);
	}

	@Test
	public void applyWinnerWinnerCatnipDinner_turnManagerThrows_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		String expectedMsg = "error.invalidRound";
		EasyMock.expect(turnManager.getRoundCount()).andThrow(
				new IllegalArgumentException(expectedMsg)
		);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalArgumentException.class,
				game::applyWinnerWinnerCatnipDinner);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		EasyMock.verify(turnManager);
	}

	@Test
	public void applyWinnerWinnerCatnipDinner_called_activateWinnerWinnerCount() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		int currentRound = 1;
		EasyMock.expect(turnManager.getRoundCount()).andReturn(currentRound);

		Player currentPlayer = EasyMock.createMock(Player.class);
		currentPlayer.activateWinnerWinnerFromRound(currentRound);

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer
		);

		EasyMock.replay(game);

		game.applyWinnerWinnerCatnipDinner();

		EasyMock.verify(turnManager, currentPlayer, game);
	}

	@Test
	public void applyClone_attackUnderClone_appliesAttackAndReturnsAttack() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card attackCard = mockCardOfType(CardType.ATTACK);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("applyAttack")
				.createMock();

		game.applyAttack();
		EasyMock.expectLastCall();

		EasyMock.replay(game);

		CardType actualCardType = game.applyClone(attackCard);

		assertEquals(CardType.ATTACK, actualCardType);

		EasyMock.verify(game);
	}

	@Test
	public void applyClone_seeTheFutureUnderClone_returnsSeeTheFuture() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card seeTheFutureCard = mockCardOfType(CardType.SEE_THE_FUTURE);

		EasyMock.replay(players, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		CardType actualCardType = game.applyClone(seeTheFutureCard);

		assertEquals(CardType.SEE_THE_FUTURE, actualCardType);
	}

	@Test
	public void getAliveIndices_noAlivePlayers_returnEmptySet() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.isAlive()).andStubReturn(false);
		EasyMock.expect(player2.isAlive()).andStubReturn(false);

		EasyMock.replay(player1, player2, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Set<Integer> expectedAliveIndices = Set.of();
		Set<Integer> actualAliveIndices = game.getAliveIndices();

		assertEquals(expectedAliveIndices, actualAliveIndices);
	}

	@Test
	public void getAliveIndices_oneAlivePlayer_returnAliveIndices() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Player player3 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2, player3);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.isAlive()).andStubReturn(true);
		EasyMock.expect(player2.isAlive()).andStubReturn(false);
		EasyMock.expect(player3.isAlive()).andStubReturn(false);

		EasyMock.replay(player1, player2, player3, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Set<Integer> expectedAliveIndices = Set.of(0);
		Set<Integer> actualAliveIndices = game.getAliveIndices();

		assertEquals(expectedAliveIndices, actualAliveIndices);
	}

	@Test
	public void getAliveIndices_twoAlivePlayers_returnAliveIndices() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Player player3 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2, player3);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.isAlive()).andStubReturn(true);
		EasyMock.expect(player2.isAlive()).andStubReturn(false);
		EasyMock.expect(player3.isAlive()).andStubReturn(true);

		EasyMock.replay(player1, player2, player3, drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Set<Integer> expectedAliveIndices = Set.of(0, 2);
		Set<Integer> actualAliveIndices = game.getAliveIndices();

		assertEquals(expectedAliveIndices, actualAliveIndices);
	}

	@Test
	public void getAliveIndices_allAlivePlayers_returnAliveIndices() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		Player player3 = EasyMock.createMock(Player.class);
		Player player4 = EasyMock.createMock(Player.class);

		List<Player> players = List.of(player1, player2, player3, player4);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(player1.isAlive()).andStubReturn(true);
		EasyMock.expect(player2.isAlive()).andStubReturn(true);
		EasyMock.expect(player3.isAlive()).andStubReturn(true);
		EasyMock.expect(player4.isAlive()).andStubReturn(true);

		EasyMock.replay(player1, player2, player3, player4,
				drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Set<Integer> expectedAliveIndices = Set.of(0, 1, 2, GameConstants.MAX_PLAYER_INDEX);
		Set<Integer> actualAliveIndices = game.getAliveIndices();

		assertEquals(expectedAliveIndices, actualAliveIndices);
	}

	@ParameterizedTest
	@MethodSource("provideGetWinnerNameFailedConditions")
	public void getWinnerName_notExactlyOneAlive_failed(
			List<String> playerNames, Set<Integer> expectedAliveIndices) {

		String expectedMsg = "error.noWinner";

		List<Player> players = mockPlayersWithNamesAndIsAlive(
				playerNames, expectedAliveIndices);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		Exception exception = assertThrows(IllegalStateException.class,
				game::getWinnerName);

		String actualMsg = exception.getMessage();
		assertEquals(expectedMsg, actualMsg);

		players.forEach(EasyMock::verify);
	}

	private static Stream<Arguments> provideGetWinnerNameFailedConditions() {
		return Stream.of(
				Arguments.of(List.of("Alice", "Bob"), Set.of(0, 1)),
				Arguments.of(List.of("Alice", "Alice", "Audrey", "Turkey"),
						Set.of(2)),
				Arguments.of(List.of("Alice", "Alice", "Alive", "Steve"),
						Set.of())
		);
	}

	@ParameterizedTest
	@MethodSource("provideGetWinnerNameSuccessConditions")
	public void getWinnerName_oneAlive_returnWinnerName(
			List<String> playerNames, Set<Integer> expectedAliveIndices,
			String expectedWinnerName) {

		List<Player> players = mockPlayersWithNamesAndIsAlive(
				playerNames, expectedAliveIndices);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.replay(drawPile, discardPile, turnManager);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		String actualWinnerName = game.getWinnerName();

		assertEquals(expectedWinnerName, actualWinnerName);

		players.forEach(EasyMock::verify);
	}

	private static Stream<Arguments> provideGetWinnerNameSuccessConditions() {
		return Stream.of(
				Arguments.of(List.of("Jeff", "Jeff"), Set.of(0), "Jeff"),
				Arguments.of(List.of("Audrey", "Jeff", "Chicken"),
						Set.of(1, 2), "Audrey")
		);
	}

	@Test
	public void drawRecycle_nonExplodingCard_cardDrawnToHand() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = mockCardOfType(CardType.SKIP);
		Player currentPlayer = EasyMock.createMock(Player.class);

		discardPile.shuffle();
		EasyMock.expectLastCall();

		EasyMock.expect(discardPile.peekBottom()).andReturn(card);
		EasyMock.expect(discardPile.removeBottom()).andReturn(card);

		currentPlayer.addCardToHand(card);
		EasyMock.expectLastCall();

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawRecycle();

		assertEquals(card, actualCard);

		EasyMock.verify(discardPile, turnManager, currentPlayer, game);
	}

	@Test
	public void drawRecycle_explodingKitten_cardNotAddedToHand() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		Card card = mockCardOfType(CardType.EXPLODING_KITTEN);
		Player currentPlayer = EasyMock.createMock(Player.class);

		discardPile.shuffle();
		EasyMock.expectLastCall();

		EasyMock.expect(discardPile.peekBottom()).andReturn(card);

		turnManager.decrementDrawCount();
		EasyMock.expectLastCall();

		currentPlayer.deselectHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(players, drawPile, discardPile, turnManager, currentPlayer);

		Game game = mockGameWithGetCurrentPlayer(
				players, drawPile, discardPile, turnManager, currentPlayer);

		EasyMock.replay(game);

		Card actualCard = game.drawRecycle();

		assertEquals(card, actualCard);

		EasyMock.verify(discardPile, turnManager, currentPlayer, game);
	}

	private Game mockGameWithGetCurrentPlayer(
			List<Player> players, Deck drawPile, Deck discardPile,
			TurnManager turnManager, Player currentPlayer) {
		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);

		return game;
	}

	private Game mockGameWithGetCurrentPlayerAndCanPlaySelected(
			List<Player> players, Deck drawPile, Deck discardPile,
			TurnManager turnManager, Player currentPlayer) {

		Game game = EasyMock.createMockBuilder(Game.class)
				.withConstructor(players, drawPile, discardPile, turnManager)
				.addMockedMethod("canPlaySelected")
				.addMockedMethod("getCurrentPlayer")
				.createMock();

		EasyMock.expect(game.canPlaySelected()).andStubReturn(true);
		EasyMock.expect(game.getCurrentPlayer()).andStubReturn(currentPlayer);

		return game;
	}

	private List<Player> mockPlayersWithNamesAndIsAlive(
			List<String> playerNames, Set<Integer> expectedAliveIndices) {

		List<Player> players = new ArrayList<>();

		for (int i = 0; i < playerNames.size(); i++) {
			Player player = EasyMock.createMock(Player.class);

			boolean isAlive = !expectedAliveIndices.contains(i);
			EasyMock.expect(player.isAlive()).andReturn(isAlive).atLeastOnce();

			String name = playerNames.get(i);
			EasyMock.expect(player.getName()).andStubReturn(name);

			players.add(player);
			EasyMock.replay(player);
		}

		return players;
	}

	private List<Player> mockPlayersWithExplodingExpectations(
			int numPlayers, int currentPlayerIndex, Set<Integer> expectedAliveIndices) {

		List<Player> players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			Player player = EasyMock.createMock(Player.class);

			boolean isAlive = expectedAliveIndices.contains(i);
			EasyMock.expect(player.isAlive()).andReturn(isAlive).atLeastOnce();

			if (i == currentPlayerIndex) {
				player.deselectHandCards();
				EasyMock.expectLastCall();

				player.eliminate();
				EasyMock.expectLastCall();
			}

			players.add(player);
			EasyMock.replay(player);
		}

		return players;
	}

	private static List<Card> mockCardsOfTypes(List<CardType> cardTypes) {
		List<Card> selectedCards = new ArrayList<>();

		for (CardType cardType : cardTypes) {
			Card card = mockCardOfType(cardType);

			selectedCards.add(card);
		}

		return selectedCards;
	}

	private static Card mockCardOfType(CardType cardType) {
		Card card = EasyMock.createMock(Card.class);
		EasyMock.expect(card.getType()).andStubReturn(cardType);
		EasyMock.replay(card);

		return card;
	}

	private static List<Card> mockCardsWithIds(List<String> cardIds) {
		List<Card> selectedCards = new ArrayList<>();

		for (String cardId : cardIds) {
			Card card = mockCardWithId(cardId);

			selectedCards.add(card);
		}

		return selectedCards;
	}

	private static Card mockCardWithId(String cardId) {
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