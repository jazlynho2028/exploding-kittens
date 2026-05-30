package ui;

import domain.Game;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerDeckControllerTests {

	private static final List<String> currentPlayerHandIds = List.of();
	private static final List<String> playerNames = List.of();
	private static final boolean canDraw = true;
	private static final int currentPlayerIndex = 0;
	private static final boolean isGameOngoing = true;
	private static final boolean isDrawPileEmpty = true;
	private static final boolean canPlaySelected = true;
	private static final String expectedMsg = "An error occurred.";

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

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, canDraw);
		EasyMock.expectLastCall();

		view.buildAddRenderPlayerNameTags(playerNames, currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.buildDependentUI();

		EasyMock.verify(model, view);
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

	@Test
	public void onNameTag_playerStaysTheSame_noChange() {
		int playerIndex = 0;

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onNameTag(playerIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_playerChanges_success() {
		int playerIndex = 1;
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);

		controller.handleChangeCurrentPlayer(playerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void onNameTag_called_failed() {
		int playerIndex = 0;
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getCurrentPlayerIndex()).andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, onError);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_success() {
		int playerIndex = 0;
		boolean isFaceUp = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		setUpRenderPlayerNameTagsExpectations();
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		model.changeCurrentPlayerIndex(playerIndex);
		EasyMock.expectLastCall();

		model.setFaceUpToFalse();
		EasyMock.expectLastCall();

		view.renderPlayerNameTags(currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();

		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, view, controller);
	}

	private void setUpRenderPlayerNameTagsExpectations() {
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
	}

	@Test
	public void rebindHandCards_called_success() {
		boolean isFaceUp = true;

		setUpBuildAndAddPlayerHandCardsExpectations(isFaceUp);

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, canDraw);
		EasyMock.expectLastCall();

		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.rebindHandCards();

		EasyMock.verify(model, view);
	}

	@Test
	public void onDrawPile_drawsCard_success() {
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		model.drawFromPile();
		EasyMock.expectLastCall();

		setUpRenderDrawPileExpectations();
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onDrawPile();

		EasyMock.verify(model, view, controller);
	}

	private void setUpRenderDrawPileExpectations() {
		EasyMock.expect(model.getCanDraw()).andReturn(canDraw);
		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
	}

	private void setUpRenderTurnControlSectionExpectations(boolean canEndTurn) {
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
	}

	@Test
	public void onDrawPile_drawsCard_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.drawFromPile();
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

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
		boolean isFaceUp = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		model.setIsFaceUpToOpposite();
		EasyMock.expectLastCall();

		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onHandVisibilityButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onHandVisibilityButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.setIsFaceUpToOpposite();
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onHandVisibilityButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceUp_success() {
		int handCardIndex = 0;
		boolean isFaceUp = true;
		boolean canEndTurn = false;

		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		model.setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, view);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility() {
		int handCardIndex = 0;
		boolean isFaceUp = false;
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("onHandVisibilityButton")
				.createMock();

		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		controller.onHandVisibilityButton();
		EasyMock.expectLastCall();

		EasyMock.replay(controller);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(controller);
	}

	@Test
	public void onPlayerHandCardButton_called_failed() {
		int handCardsIndex = 0;
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getIsFaceUp()).andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onPlayerHandCardButton(handCardsIndex);

		EasyMock.verify(model, onError);
	}

	@Test
	public void onStartGameButton_called_success() {
		int startingPlayerIndex = 0;
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		EasyMock.expect(model.getStartingPlayerIndex()).andReturn(startingPlayerIndex);
		setUpRenderDrawPileExpectations();
		setUpBuildAndRenderTurnControlSectionExpectations(canEndTurn);

		model.startGame();
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(startingPlayerIndex);
		EasyMock.expectLastCall();

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		view.buildAndRenderTurnControlSection(isGameOngoing, canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onStartGameButton();

		EasyMock.verify(model, view, controller);
	}

	private void setUpBuildAndRenderTurnControlSectionExpectations(boolean canEndTurn) {
		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
		setUpRenderTurnControlSectionExpectations(canEndTurn);
	}

	@Test
	public void onStartGameButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.startGame();
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onStartGameButton();

		EasyMock.verify(model, onError);
	}

}