package ui;

import domain.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class PlayerDeckControllerTests {

	@Test
	public void onNameTag_playerStaysTheSame_noChange() {
		Game model = EasyMock.createMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(0);

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		int playerIndex = 0;

		controller.onNameTag(playerIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_playerChanges_success() {
		Game model = EasyMock.createMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();
		int playerIndex = 0;

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(1);
		controller.handleChangeCurrentPlayer(playerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();
		int playerIndex = 0;

		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall();
		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_fail() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		int playerIndex = 0;
		String expectedMsg = "Failed to change current player.";

		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, onError);
	}

	@Test
	public void onDrawPile_drawsCard_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();

		model.drawFromPile();
		EasyMock.expectLastCall();
		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onDrawPile();

		EasyMock.verify(model, controller);
	}

	@Test
	public void onDrawPile_drawsCard_fail() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		String expectedMsg = "Failed to draw from pile.";

		model.drawFromPile();
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onDrawPile();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onHandVisibilityButton_called_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();

		model.setIsFaceUpToOpposite();
		EasyMock.expectLastCall();
		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onHandVisibilityButton();

		EasyMock.verify(model, controller);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility() {
		Game model = EasyMock.createMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("onHandVisibilityButton")
				.createMock();
		int handCardIndex = 0;

		EasyMock.expect(model.getIsFaceUp()).andReturn(false);
		controller.onHandVisibilityButton();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceUp_callsModelMethod() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		int handCardIndex = 0;

		EasyMock.expect(model.getIsFaceUp()).andReturn(true);
		model.setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onStartGameButton_called_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();
		int startingPlayerIndex = 0;

		model.startGame();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getStartingPlayerIndex()).andReturn(startingPlayerIndex);
		controller.handleChangeCurrentPlayer(startingPlayerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onStartGameButton();

		EasyMock.verify(model, controller);
	}

	@Test
	public void onStartGameButton_called_fail() {
		Game model = EasyMock.createMock(Game.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		String expectedMsg = "Failed to start game.";

		model.startGame();
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onStartGameButton();

		EasyMock.verify(model, onError);
	}

}
