package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class WinnerWinnerCatnipDinnerTests {

	private List<Player> players;
	private Deck drawPile;
	private Deck discardPile;
	private TurnManager turnManager;
	private Game game;
	private Player targetPlayer;

	@Test
	public void winnerWinnerCatnipDinner_notActivatedAndRequirementFulfilled_win() {
		int numPlayers = 2;
		int targetPlayerIndex = 0;
		String expectedWinnerName = "Monkey";

		setUpStartGame(numPlayers, targetPlayerIndex, expectedWinnerName);

		int numRoundsBeforeTest = GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numTurnsBeforeTest = calculateNumTurns(
				numPlayers, numRoundsBeforeTest, targetPlayerIndex);

		addCardsToDrawPile(drawPile, numTurnsBeforeTest);
		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);

		int expectedPriorActivationRound = 0;
		int actualPriorActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedPriorActivationRound,
				actualPriorActivationRound);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedActivationRound = 1;
		int actualActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedActivationRound, actualActivationRound);

		advanceTurns(game, numTurnsBeforeTest);

		assertIsAliveOnWin(players, targetPlayerIndex);

		assertFalse(game.getIsGameOngoing());

		String actualWinnerName = game.getWinnerName();
		assertEquals(expectedWinnerName, actualWinnerName);
	}

	@Test
	public void winnerWinnerCatnipDinner_activatedAndRequirementFulfilled_win() {
		int numPlayers = GameConstants.MAX_PLAYERS - 1;
		int targetPlayerIndex = 2;
		String expectedWinnerName = "Monkey";

		setUpStartGame(numPlayers, targetPlayerIndex, expectedWinnerName);

		int numRoundsBeforeTest = 1 + GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numTurnsBeforeTest = calculateNumTurns(
				numPlayers, numRoundsBeforeTest, targetPlayerIndex);

		addCardsToDrawPile(drawPile, numTurnsBeforeTest);

		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);
		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);

		advanceTurns(game, targetPlayerIndex);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedPriorActivationRound = 1;
		int actualPriorActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedPriorActivationRound,
				actualPriorActivationRound);

		advanceTurns(game, numPlayers);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedActivationRound = 2;
		int actualActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedActivationRound, actualActivationRound);

		int numRemainingRounds = GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numRemainingTurns = numPlayers * numRemainingRounds + 1;
		advanceTurns(game, numRemainingTurns);

		assertIsAliveOnWin(players, targetPlayerIndex);

		assertFalse(game.getIsGameOngoing());

		String actualWinnerName = game.getWinnerName();
		assertEquals(expectedWinnerName, actualWinnerName);
	}

	@Test
	public void winnerWinnerCatnipDinner_notActivatedAndRequirementNotFulfilled_reset() {
		int numPlayers = GameConstants.MAX_PLAYERS;
		int targetPlayerIndex = 0;
		String expectedWinnerName = "Monkey";

		setUpStartGame(numPlayers, targetPlayerIndex, expectedWinnerName);

		int numRoundsBeforeTest = GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numTurnsBeforeTest = calculateNumTurns(
				numPlayers, numRoundsBeforeTest, targetPlayerIndex);

		addCardsToDrawPile(drawPile, numTurnsBeforeTest);

		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);
		addCardToPlayerHand(targetPlayer, CardType.SEE_THE_FUTURE);

		int expectedPriorActivationRound = 0;
		int actualPriorActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedPriorActivationRound,
				actualPriorActivationRound);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedActivationRound = 0;
		int actualActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedActivationRound, actualActivationRound);

		assertAllAlive(players);

		assertTrue(game.getIsGameOngoing());

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.getWinnerName());

		String expectedMsg = "error.noWinner";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
	}

	@Test
	public void winnerWinnerCatnipDinner_activatedAndRequirementNotFulfilled_reset() {
		int numPlayers = 2;
		int targetPlayerIndex = 1;
		String expectedWinnerName = "Monkey";

		setUpStartGame(numPlayers, targetPlayerIndex, expectedWinnerName);

		int numRoundsBeforeTest = 1 + GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numTurnsBeforeTest = calculateNumTurns(
				numPlayers, numRoundsBeforeTest, targetPlayerIndex);

		addCardsToDrawPile(drawPile, numTurnsBeforeTest - 1);
		String cardId = DeckBuilder.createCardId(CardType.EXPLODING_KITTEN, 1);
		Card explodingKitten = new Card(cardId, CardType.EXPLODING_KITTEN);
		drawPile.addCardToBottom(explodingKitten);

		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);
		addCardToPlayerHand(targetPlayer, CardType.WINNER_WINNER_CATNIP_DINNER);
		addCardToPlayerHand(targetPlayer, CardType.DEFUSE);

		advanceTurns(game, targetPlayerIndex);
		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedPriorActivationRound = 1;
		int actualPriorActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedPriorActivationRound,
				actualPriorActivationRound);

		advanceTurns(game, numPlayers);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedActivationRound = 2;
		int actualActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedActivationRound, actualActivationRound);

		int numRemainingRounds = GameConstants.WINNER_WINNER_REQUIRED_ROUNDS;
		int numRemainingTurns = numPlayers * numRemainingRounds;
		advanceTurns(game, numRemainingTurns);

		game.drawFromPile();
		game.playDefuse(0);
		game.endTurn();

		assertAllAlive(players);

		assertTrue(game.getIsGameOngoing());

		Exception exception = assertThrows(IllegalStateException.class, () ->
				game.getWinnerName());

		String expectedMsg = "error.noWinner";
		String actualMsg = exception.getMessage();

		assertEquals(expectedMsg, actualMsg);
	}

	private List<Player> createPlayers(
			int numPlayers, int targetPlayerIndex, String expectedWinnerName) {

		List<Player> players = new ArrayList<>();

		for (int i = 0; i < numPlayers; i++) {
			String name = "";

			if (i == targetPlayerIndex) {
				name = expectedWinnerName;
			}
			else {
				name = "Steve";
			}

			Player player = new Player(name);
			players.add(player);
		}

		return players;
	}

	private void setUpStartGame(
			int numPlayers, int targetPlayerIndex, String expectedWinnerName) {

		players = createPlayers(
				numPlayers,
				targetPlayerIndex,
				expectedWinnerName
		);

		targetPlayer = players.get(targetPlayerIndex);

		Random random = new Random();

		drawPile = new Deck(new ArrayDeque<>(), random);
		discardPile = new Deck(new ArrayDeque<>(), random);
		turnManager = new TurnManager(numPlayers);

		game = new Game(players, drawPile, discardPile, turnManager);
		game.startGame();
	}

	private int calculateNumTurns(
			int numPlayers, int numRoundsBeforeTest, int targetPlayerIndex) {

		return numPlayers * numRoundsBeforeTest +
				targetPlayerIndex + 1;
	}

	private void addCardsToDrawPile(Deck drawPile, int numCardsToAdd) {
		CardType cardType = CardType.SEE_THE_FUTURE;

		for (int i = 0; i < numCardsToAdd; i++) {
			String cardId = DeckBuilder.createCardId(cardType, i + 1);
			Card card = new Card(cardId, cardType);
			drawPile.addCardToTop(card);
		}
	}

	private void addCardToPlayerHand(Player player, CardType cardType) {
		String cardId = DeckBuilder.createCardId(
				cardType, 1);
		Card card = new Card(cardId, cardType);

		player.addCardToHand(card);
	}

	private void advanceTurns(Game game, int numTurns) {
		for (int i = 0; i < numTurns; i++) {
			game.drawFromPile();
			game.endTurn();
		}
	}

	private void assertIsAliveOnWin(List<Player> players, int targetPlayerIndex) {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			boolean isTargetPlayer = (i == targetPlayerIndex);
			assertEquals(player.isAlive(), isTargetPlayer);
		}
	}

	private void assertAllAlive(List<Player> players) {
		for (Player player : players) {
			assertTrue(player.isAlive());
		}
	}

}
