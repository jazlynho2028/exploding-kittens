package domain;

import io.cucumber.java.bs.A;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

			player.addCardToHand(defuseCard(NUM_DEFUSES - i));
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

	private static Card defuseCard(int idNum) {
		EasyMock.reportMatcher(new IArgumentMatcher() {
			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof Card)) {
					return false;
				}

				Card card = (Card) argument;
				return hasSameDefuseCardFields(card, idNum);
			}

			@Override
			public void appendTo(StringBuffer buffer) {
				buffer.append("isDefuseCard()");
			}
		});
		return new Card("DEFUSE_INVALID", CardType.DEFUSE);
	}

	private static boolean hasSameDefuseCardFields(Card card, int idNum) {
		boolean isDefuse = (card.getType() == CardType.DEFUSE);
		boolean hasValidId = Objects.equals(
				card.getId(), String.format("%s%d", "DEFUSE_", idNum));

		return isDefuse && hasValidId;
	}

}