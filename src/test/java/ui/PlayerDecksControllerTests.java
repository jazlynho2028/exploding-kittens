package ui;

import domain.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class PlayerDecksControllerTests {

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

}
