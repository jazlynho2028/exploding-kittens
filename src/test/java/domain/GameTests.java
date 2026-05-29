package domain;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.jupiter.api.Test;

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

	@Test
	public void constructor_twoPlayers_initializeGame() {
		Player player1 = EasyMock.createMock(Player.class);
		Player player2 = EasyMock.createMock(Player.class);
		List<Player> players = List.of(player1, player2);

		Deck drawPile = EasyMock.createMock(Deck.class);
		Deck discardPile = EasyMock.createMock(Deck.class);
		TurnManager turnManager = EasyMock.createMock(TurnManager.class);

		player1.addCardToHand(defuseCard(FIVE_CARDS));
		EasyMock.expectLastCall();
		player2.addCardToHand(defuseCard(FOUR_CARDS));
		EasyMock.expectLastCall();

		for (Player player : players) {
			for (int i = 0; i < STARTING_HAND_SIZE - 1; i++) {
				Card card = EasyMock.createMock(Card.class);
				EasyMock.expect(drawPile.removeTop()).andReturn(card);

				player.addCardToHand(card);
				EasyMock.expectLastCall();

				EasyMock.replay(card);
			}
		}

		EasyMock.replay(player1, player2, drawPile);

		Game game = new Game(players, drawPile, discardPile, turnManager);

		assertFalse(game.getIsGameOngoing());
		assertFalse(game.getIsFaceUp());

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