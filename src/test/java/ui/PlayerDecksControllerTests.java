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

}
