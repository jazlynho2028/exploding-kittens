package ui;

import domain.Card;
import domain.CardType;
import domain.Game;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import domain.GameConstants;

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

		view.bindPlayCardsButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.bindEndTurnButton(EasyMock.anyObject());
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
	public void onDrawPile_drawNonExplodingCard_success() {
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.DEFUSE);
		EasyMock.expect(model.drawFromPile()).andReturn(drawnCard);
		EasyMock.expectLastCall();

		setUpRenderDrawPileExpectations();
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);

		EasyMock.replay(model, view, controller, drawnCard);

		controller.onDrawPile();

		EasyMock.verify(model, view, controller, drawnCard);
	}

	private void setUpRenderDrawPileExpectations() {
		EasyMock.expect(model.getCanDraw()).andReturn(canDraw);
		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
	}

	private void setUpRenderTurnControlSectionExpectations(boolean canEndTurn) {
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void onDrawPile_drawExplodingCard_buildExplodeOverlay(boolean hasDefuse) {
		String drawnCardId = "EXPLODINGKITTEN_1";
		int drawPileSize = 0;

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.EXPLODING_KITTEN);
		EasyMock.expect(drawnCard.getId()).andReturn(drawnCardId);

		EasyMock.expect(model.getDrawPileSize()).andReturn(drawPileSize);
		EasyMock.expect(model.drawFromPile()).andReturn(drawnCard);
		EasyMock.expect(model.currentPlayerHasDefuse()).andReturn(hasDefuse);

		if (hasDefuse) {
			view.bindDefuseButton(EasyMock.anyObject());
		}
		else {
			view.bindExplodeButton(EasyMock.anyObject());
		}
		EasyMock.expectLastCall();

		int drawPileSizeAfterDrawExplodingKitten = drawPileSize - 1;
		view.buildExplodeOverlay(hasDefuse, drawnCardId,
				drawPileSizeAfterDrawExplodingKitten);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, drawnCard);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onDrawPile();

		EasyMock.verify(model, view, drawnCard);
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

		model.toggleFaceUp();
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

		model.toggleFaceUp();
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

		model.toggleSelectedPlayerCardAt(handCardIndex);
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

	@Test
	public void onPlayCardsButton_noAdditionalUIChange_success() {
		boolean canDrawFromDiscard = true;
		boolean canEndTurn = true;
		String topDiscardId = "DOUBLEUP_1";
		CardType topDiscardType = CardType.DOUBLE_UP;

		EasyMock.expect(model.canDrawFromDiscard()).andReturn(canDrawFromDiscard);
		EasyMock.expect(model.getTopDiscardId()).andReturn(topDiscardId);

		setUpRenderTurnControlSectionExpectations(canEndTurn);

		EasyMock.expect(model.playSelectedCards()).andReturn(topDiscardType);

		view.renderDiscardPile(canDrawFromDiscard, topDiscardId);
		EasyMock.expectLastCall();

		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_skipPlayed_updatedPlayer() {
		boolean canDrawFromDiscard = true;
		boolean canEndTurn = true;
		String topDiscardId = "SKIP_1";
		CardType topDiscardType = CardType.SKIP;

		EasyMock.expect(model.canDrawFromDiscard()).andReturn(canDrawFromDiscard);
		EasyMock.expect(model.getTopDiscardId()).andReturn(topDiscardId);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);

		setUpRenderTurnControlSectionExpectations(canEndTurn);

		EasyMock.expect(model.playSelectedCards()).andReturn(topDiscardType);

		view.renderDiscardPile(canDrawFromDiscard, topDiscardId);
		EasyMock.expectLastCall();

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(currentPlayerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_godcatPlayed_overlayShown() {
		boolean canDrawFromDiscard = true;
		boolean canEndTurn = true;
		String topDiscardId = "GODCAT_1";
		CardType topDiscardType = CardType.GODCAT;

		EasyMock.expect(model.canDrawFromDiscard()).andReturn(canDrawFromDiscard);
		EasyMock.expect(model.getTopDiscardId()).andReturn(topDiscardId);

		setUpRenderTurnControlSectionExpectations(canEndTurn);

		EasyMock.expect(model.playSelectedCards()).andReturn(topDiscardType);

		view.renderDiscardPile(canDrawFromDiscard, topDiscardId);
		EasyMock.expectLastCall();

		view.bindGodcatConfirmButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.buildGodcatOverlay(GameConstants.GODCAT_CARDTYPE_OPTIONS);
		EasyMock.expectLastCall();

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.playSelectedCards();
		EasyMock.expectLastCall().andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onPlayCardsButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onEndTurnButton_called_success() {
		int currentPlayerIndex = 0;
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		setUpRenderDrawPileExpectations();
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		model.advanceTurn();
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(currentPlayerIndex);
		EasyMock.expectLastCall();

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onEndTurnButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onEndTurnButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.advanceTurn();
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onEndTurnButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onDefuseButton_called_success() {
		int currentPlayerIndex = 0;
		boolean isFaceUp = true;
		boolean canEndTurn = true;
		int explodingKittenInsertIndex = 0;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		EasyMock.expect(view.getExplodingKittenInsertIndex()).andReturn(
				explodingKittenInsertIndex);
		setUpBuildAndAddPlayerHandCardsExpectations(isFaceUp);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		setUpRenderDrawPileExpectations();
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		model.playDefuse(explodingKittenInsertIndex);
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, canDraw);

		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(currentPlayerIndex);
		EasyMock.expectLastCall();

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onDefuseButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onDefuseButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		int explodingKittenInsertIndex = 0;
		EasyMock.expect(view.getExplodingKittenInsertIndex()).andReturn(
				explodingKittenInsertIndex);

		model.playDefuse(explodingKittenInsertIndex);
		EasyMock.expectLastCall().andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onDefuseButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onExplodeButton_called_success() {
		int currentPlayerIndex = 0;
		boolean canEndTurn = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();


		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		setUpRenderDrawPileExpectations();
		setUpRenderTurnControlSectionExpectations(canEndTurn);

		model.playExplode();
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(currentPlayerIndex);
		EasyMock.expectLastCall();

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onExplodeButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onExplodeButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.playExplode();
		EasyMock.expectLastCall().andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onExplodeButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onGodcatConfirm_validCardType_success() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("onConfirmGodcatCard")
				.createMock();

		EasyMock.expect(view.getSelectedGodcatCardType()).andReturn(CardType.ATTACK);

		controller.onConfirmGodcatCard(CardType.ATTACK);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onGodcatConfirm();

		EasyMock.verify(view, controller);
	}

	@Test
	public void onGodcatConfirm_modelThrowsException_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(view.getSelectedGodcatCardType()).andThrow(
				new RuntimeException(expectedMsg)
		);

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onGodcatConfirm();

		EasyMock.verify(view, onError);
	}

	@Test
	public void onConfirmGodcatCard_validCardType_applyGodcatCalled() {
		model.applyGodcat(CardType.ATTACK);
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onConfirmGodcatCard(CardType.ATTACK);

		EasyMock.verify(model, view);
	}

	@Test
	public void onConfirmGodcatCard_modelThrowsException_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.applyGodcat(CardType.EXPLODING_KITTEN);
		EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onConfirmGodcatCard(CardType.EXPLODING_KITTEN);

		EasyMock.verify(model, onError);
	}

}