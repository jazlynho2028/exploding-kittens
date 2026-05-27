package ui;

import domain.Game;
import domain.GameConstants;
import io.cucumber.java.an.E;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerDeckControllerTests {

	private final List<String> currentPlayerHandIds = List.of();
	private final List<String> playerNames = List.of();
	private final boolean canDraw = true;
	private final int currentPlayerIndex = 0;
	private final boolean isGameOngoing = true;
	private final boolean isDrawPileEmpty = true;
	private final boolean canPlaySelected = true;
	private final String expectedMsg = "An error occurred.";

	private Game model;
	private PlayerDeckView view;

	@BeforeEach
	public void setUp() {
		model = EasyMock.createMock(Game.class);
		view = EasyMock.createMock(PlayerDeckView.class);
	}

	@Test
	public void buildPlayerDeckScene_called_success() {
		Scene expectedScene = EasyMock.createMock(Scene.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("buildDependentUI")
				.addMockedMethod("bindUI")
				.createMock();

		controller.buildDependentUI();
		EasyMock.expectLastCall();

		controller.bindUI();
		EasyMock.expectLastCall();

		EasyMock.expect(view.createPlayerDeckScene()).andReturn(expectedScene);

		EasyMock.replay(view, controller);

		Scene actualScene = controller.buildPlayerDeckScene();

		assertEquals(expectedScene, actualScene);

		EasyMock.verify(view, controller);
	}

	@Test
	public void buildPlayerDeckScene_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("buildDependentUI")
				.createMock();

		controller.buildDependentUI();
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(onError, controller);

		controller.setOnError(onError);
		controller.buildPlayerDeckScene();

		EasyMock.verify(onError, controller);
	}

	@Test
	public void buildDependentUI_called_success() {
		boolean isFaceUp = true;
		setUpBuildAndAddPlayerHandCardsExpectations(isFaceUp);
		setUpBuildAddRenderPlayerNameTagsExpectations();

		EasyMock.replay(model);

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, canDraw);
		EasyMock.expectLastCall();

		view.buildAddRenderPlayerNameTags(playerNames, currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();

		EasyMock.replay(view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.buildDependentUI();

		EasyMock.verify(view, model);
	}

	private void setUpBuildAndAddPlayerHandCardsExpectations(boolean isFaceUp) {
		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(currentPlayerHandIds);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.getCanDraw()).andReturn(canDraw);
	}

	private void setUpBuildAddRenderPlayerNameTagsExpectations() {
		EasyMock.expect(model.getPlayerNames()).andReturn(playerNames);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
	}

	@Test
	public void bindUI_called_success() {
		view.bindDrawPileButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.bindHandVisibilityButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.bindStartGameButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.bindNameTags(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.replay(view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.bindUI();

		EasyMock.verify(view);
	}

//
//	@Test
//	public void constructor_called_success() {
//		String expectedMsg = "error.test";
//
//		int playerIndex = 0;
//		model.changeCurrentPlayerIndex(playerIndex);
//		EasyMock.expectLastCall();
//
//		model.setFaceUpToFalse();
//		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//
//		assertDoesNotThrow(() -> controller.handleChangeCurrentPlayer(playerIndex));
//
//		EasyMock.verify(model, view);
//	}
//
//	@Test
//	public void onNameTag_playerStaysTheSame_noChange() {
//		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//		int playerIndex = 0;
//		controller.onNameTag(playerIndex);
//
//		EasyMock.verify(model, view);
//	}
//
//	@Test
//	public void onNameTag_playerChanges_success() {
//		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//				PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("handleChangeCurrentPlayer")
//				.createMock();
//		int playerIndex = 1;
//
//		controller.handleChangeCurrentPlayer(playerIndex);
//		EasyMock.expectLastCall();
//		EasyMock.replay(controller);
//
//		controller.onNameTag(playerIndex);
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void handleChangeCurrentPlayer_playerChanges_success() {
//		int playerIndex = 0;
//		model.changeCurrentPlayerIndex(playerIndex);
//		EasyMock.expectLastCall();
//		model.setFaceUpToFalse();
//		EasyMock.expectLastCall();
//		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
//		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
//		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
//
//		view.renderPlayerNameTags(currentPlayerIndex, isGameOngoing);
//		EasyMock.expectLastCall();
//		view.renderHandVisibilityButton(isFaceUp);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//				PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("buildAddBindPlayerHandCards")
//				.createMock();
//
//		controller.buildAddBindPlayerHandCards();
//		EasyMock.expectLastCall();
//		EasyMock.replay(controller);
//
//		controller.handleChangeCurrentPlayer(playerIndex);
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void handleChangeCurrentPlayer_playerChanges_fail() {
//		Consumer<String> onError = EasyMock.createMock(Consumer.class);
//		int playerIndex = 0;
//
//		String expectedMsg = "error.test";
//
//		model.changeCurrentPlayerIndex(playerIndex);
//		EasyMock.expectLastCall();
//		model.setFaceUpToFalse();
//		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));
//
//		onError.accept(expectedMsg);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view, onError);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//		controller.setOnError(onError);
//
//		controller.handleChangeCurrentPlayer(playerIndex);
//
//		EasyMock.verify(model, view, onError);
//	}
//
//	@Test
//	public void buildAddBindPlayerHandCards_called_success() {
//		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(currentPlayerHandIds);
//		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
//		EasyMock.expect(model.getIsBeforeDraw()).andReturn(isBeforeDraw);
//
//		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, isBeforeDraw);
//		EasyMock.expectLastCall();
//		view.bindPlayerHandCardButtons(EasyMock.anyObject());
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//
//		controller.buildAddBindPlayerHandCards();
//
//		EasyMock.verify(model, view);
//	}
//
//	@Test
//	public void onDrawPile_drawsCard_success() {
//		canDraw = true;
//		isDrawPileEmpty = true;
//		canPlaySelected = true;
//		canEndTurn = true;
//
//		EasyMock.expect(model.getCanDraw()).andReturn(canDraw);
//		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
//		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
//		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
//
//		model.drawFromPile();
//		EasyMock.expectLastCall();
//
//		view.renderDrawPile(canDraw, isDrawPileEmpty);
//		EasyMock.expectLastCall();
//		view.renderTurnControlSection(canPlaySelected, canEndTurn);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//						PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("buildAddBindPlayerHandCards")
//				.createMock();
//
//		controller.buildAddBindPlayerHandCards();
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(controller);
//
//		controller.onDrawPile();
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void onDrawPile_drawsCard_fail() {
//		Consumer<String> onError = EasyMock.createMock(Consumer.class);
//
//		String expectedMsg = "error.test";
//
//		model.drawFromPile();
//		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));
//		onError.accept(expectedMsg);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view, onError);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//		controller.setOnError(onError);
//
//		controller.onDrawPile();
//
//		EasyMock.verify(model, view, onError);
//	}
//
//	@Test
//	public void onHandVisibilityButton_called_success() {
//		model.setIsFaceUpToOpposite();
//		EasyMock.expectLastCall();
//
//		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
//		view.renderHandVisibilityButton(isFaceUp);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//						PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("buildAddBindPlayerHandCards")
//				.createMock();
//
//		controller.buildAddBindPlayerHandCards();
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(controller);
//
//		controller.onHandVisibilityButton();
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility() {
//		isFaceUp = false;
//		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//						PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("onHandVisibilityButton")
//				.createMock();
//		int handCardIndex = 0;
//
//		controller.onHandVisibilityButton();
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(controller);
//
//		controller.onPlayerHandCardButton(handCardIndex);
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void onPlayerHandCardButton_cardsFaceUp_callsModelMethod() {
//		int handCardIndex = 0;
//		boolean isFaceUp = true;
//		boolean canPlaySelected = false;
//		boolean canEndTurn = false;
//
//		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
//		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
//		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
//
//		model.setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);
//		EasyMock.expectLastCall();
//
//		view.renderTurnControlSection(canPlaySelected, canEndTurn);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//
//		controller.onPlayerHandCardButton(handCardIndex);
//
//		EasyMock.verify(model, view);
//	}
//
//	@Test
//	public void onStartGameButton_called_success() {
//		model.startGame();
//		EasyMock.expectLastCall();
//
//		EasyMock.expect(model.getStartingPlayerIndex()).andReturn(
//				GameConstants.STARTING_PLAYER_INDEX
//		);
//		EasyMock.expect(model.getCanDraw()).andReturn(canDraw);
//		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
//		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
//		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
//		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
//
//		view.renderDrawPile(canDraw, isDrawPileEmpty);
//		EasyMock.expectLastCall();
//
//		view.buildAndRenderTurnControlSection(isGameOngoing, canPlaySelected, canEndTurn);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, view);
//
//		PlayerDeckController controller = EasyMock.createMockBuilder(
//						PlayerDeckController.class
//				)
//				.withConstructor(model, view)
//				.addMockedMethod("handleChangeCurrentPlayer")
//				.createMock();
//
//		controller.handleChangeCurrentPlayer(GameConstants.STARTING_PLAYER_INDEX);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(controller);
//
//		controller.onStartGameButton();
//
//		EasyMock.verify(model, view, controller);
//	}
//
//	@Test
//	public void onStartGameButton_called_fail() {
//		Consumer<String> onError = EasyMock.createMock(Consumer.class);
//
//		String expectedMsg = "error.test";
//
//		model.startGame();
//		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));
//
//		onError.accept(expectedMsg);
//		EasyMock.expectLastCall();
//
//		EasyMock.replay(model, onError);
//
//		PlayerDeckController controller = new PlayerDeckController(model, view);
//		controller.setOnError(onError);
//
//		controller.onStartGameButton();
//
//		EasyMock.verify(model, onError);
//	}
}