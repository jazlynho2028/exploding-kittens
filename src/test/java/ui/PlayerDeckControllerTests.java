package ui;

import domain.Game;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerDeckControllerTests {

	@Test
	public void buildAndBindUI_called_success() {
		Game model = EasyMock.createMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("bindUI")
				.createMock();

		List<String> handIds = new ArrayList<>();
		boolean isFaceUp = true;
		boolean isBeforeDraw = true;

		List<String> playerNames = new ArrayList<>();
		int currentPlayerIndex = 0;
		boolean isGameOngoing = true;

		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(handIds);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.getIsBeforeDraw()).andReturn(isBeforeDraw);

		EasyMock.expect(model.getPlayerNames()).andReturn(playerNames);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.isGameOngoing()).andReturn(isGameOngoing);

		view.buildAndAddPlayerHandCards(handIds, isFaceUp, isBeforeDraw);
		EasyMock.expectLastCall();
		view.buildAddRenderPlayerNameTags(playerNames, currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();

		controller.bindUI();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.buildAndBindUI();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onNameTag_playerStaysTheSame_noChange() {
		Game model = EasyMock.createMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(0);

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		int playerIndex = 0;

		controller.onNameTag(playerIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_playerChanges_success() {
		Game model = EasyMock.createMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
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
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();
		int playerIndex = 0;
		int currentPlayerIndex = 0;
		boolean isGameOngoing = true;
		boolean isFaceUp = true;

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.isGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall();

		view.renderPlayerNameTags(currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();
		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_fail() {
		Game model = EasyMock.createNiceMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		int playerIndex = 0;

		String expectedMsg = "Failed to change current player.";
		EasyMock.expect(assets.getString("error.changePlayer")).andReturn(
				expectedMsg
		);

		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall().andThrow(new IllegalStateException());

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, assets, onError);
	}

	@Test
	public void buildAddBindPlayerHandCards_called_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("bindPlayerHandCardButtons")
				.createMock();
		List<String> currentPlayerHandIds = new ArrayList<>();
		boolean isFaceUp = true;
		boolean isBeforeDraw = true;

		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(currentPlayerHandIds);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.getIsBeforeDraw()).andReturn(isBeforeDraw);

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, isBeforeDraw);
		EasyMock.expectLastCall();

		controller.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.buildAddBindPlayerHandCards();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onDrawPile_drawsCard_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
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
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		String expectedMsg = "Failed to draw from pile.";
		EasyMock.expect(assets.getString("error.drawFromPile")).andReturn(
				expectedMsg
		);

		model.drawFromPile();
		EasyMock.expectLastCall().andThrow(new IllegalStateException());
		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.onDrawPile();

		EasyMock.verify(model, assets, onError);
	}

	@Test
	public void onHandVisibilityButton_called_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
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
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
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
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		int handCardIndex = 0;

		EasyMock.expect(model.getIsFaceUp()).andReturn(true);

		model.setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onStartGameButton_called_success() {
		Game model = EasyMock.createNiceMock(Game.class);
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
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
		AssetProvider assets = EasyMock.createMock(AssetProvider.class);
		PlayerDeckView view = EasyMock.createMock(PlayerDeckView.class);
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		String expectedMsg = "Failed to start game.";
		EasyMock.expect(assets.getString("error.startGame")).andReturn(
				expectedMsg
		);

		model.startGame();
		EasyMock.expectLastCall().andThrow(new IllegalStateException());

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.onStartGameButton();

		EasyMock.verify(model, assets, onError);
	}

}
