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

	@Test
	public void constructor_onePlayer_failed() {
		List<Player> players = EasyMock.createMock(List.class);
		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		EasyMock.expect(players.size()).andReturn(1);

		EasyMock.replay(players);

		String expectedMsg = "error.minPlayers";

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

	private static Card defuseCard(int idNum) {
		EasyMock.reportMatcher(new IArgumentMatcher() {
			@Override
			public boolean matches(Object argument) {
				if (!(argument instanceof Card)) {
					return false;
				}

				Card card = (Card) argument;
				return card.getType() == CardType.DEFUSE &&
						Objects.equals(card.getId(), "DEFUSE_" + idNum);
			}

			@Override
			public void appendTo(StringBuffer buffer) {
				buffer.append("isDefuseCard()");
			}
		});
		return new Card("DEFUSE_INVALID", CardType.DEFUSE);
	}

}