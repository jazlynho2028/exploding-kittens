package ui;

import domain.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class PlayerDecksControllerTests {

	@Test
	public void onNameTag_playerStaysTheSame_noChange() {
	//		Game model = EasyMock.createMock(Game.class);
	//		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
	//		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(0);
	//
	//		EasyMock.replay(model);
	//
	//		PlayerDeckController controller = new PlayerDeckController(model, assets);
	//		int playerIndex = 0;
	//
	//		controller.onNameTag(playerIndex);
	//
	//		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_playerChanges_success() {
	//		Game model = EasyMock.createMock(Game.class);
	//		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
	//
	//		PlayerDeckController controller = EasyMock.createMockBuilder(
	//				PlayerDeckController.class
	//				)
	//				.withConstructor(model, assets)
	//				.addMockedMethod("handleChangeCurrentPlayer")
	//				.createMock();
	//		int playerIndex = 0;
	//
	//		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(1);
	//
	//		controller.handleChangeCurrentPlayer(playerIndex);
	//		EasyMock.expectLastCall();
	//
	//		EasyMock.replay(model, controller);
	//
	//		controller.onNameTag(playerIndex);
	//
	//		EasyMock.verify(model, controller);
	}

}
