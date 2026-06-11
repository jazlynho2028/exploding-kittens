package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
		int numTurns = calculateNumTurns(
				numPlayers, numRoundsBeforeTest, targetPlayerIndex);
		allocateCards(numTurns);

		int expectedPriorActivationRound = 0;
		int actualPriorActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedPriorActivationRound,
				actualPriorActivationRound);

		targetPlayer.toggleSelectedHandCardAt(0);
		game.playSelectedCards();

		int expectedActivationRound = 1;
		int actualActivationRound = targetPlayer.getWinnerWinnerActivatedRound();

		assertEquals(expectedActivationRound, actualActivationRound);

		advanceTurns(game, numTurns);

		assertIsAliveOnWin(players, targetPlayerIndex);

		assertFalse(game.getIsGameOngoing());

		String actualWinnerName = game.getWinnerName();
		assertEquals(expectedWinnerName, actualWinnerName);
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
				targetPlayerIndex + 2;
	}

	private void allocateCards(int numTurns) {
		addCardsToDrawPile(drawPile, numTurns);

		addWinnerWinnerCardToPlayerHand(targetPlayer);
	}

	private void addCardsToDrawPile(Deck drawPile, int numCardsToAdd) {
		CardType cardType = CardType.SEE_THE_FUTURE;

		for (int i = 0; i < numCardsToAdd; i++) {
			String cardId = DeckBuilder.createCardId(cardType, i + 1);
			Card card = new Card(cardId, cardType);
			drawPile.addCardToTop(card);
		}
	}

	private void addWinnerWinnerCardToPlayerHand(Player player) {
		CardType winnerWinnerCardType = CardType.WINNER_WINNER_CATNIP_DINNER;
		String winnerWinnerCardId = DeckBuilder.createCardId(
				winnerWinnerCardType, 1);
		Card card = new Card(winnerWinnerCardId, winnerWinnerCardType);

		player.addCardToHand(card);
	}

	private void advanceTurns(Game game, int numTurns) {
		for (int i = 0; i < numTurns - 1; i++) {
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

}
