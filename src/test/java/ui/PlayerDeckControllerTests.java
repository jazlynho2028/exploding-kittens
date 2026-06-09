package ui;

import domain.Card;
import domain.CardType;
import domain.Game;
import domain.GameConstants;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.function.Consumer;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDeckControllerTests {

	private static final List<String> CURRENT_PLAYER_HAND_IDS = List.of();
	private static final List<String> PLAYER_NAMES = List.of();
	private static final boolean CAN_DRAW = true;
	private static final int CURRENT_PLAYER_INDEX = 0;
	private static final boolean IS_DRAW_PILE_EMPTY = true;
	private static final boolean CAN_PLAY_SELECTED = true;
	private static final Set<Integer> DEAD_INDICES = Set.of();
	private static final String EXPECTED_ERROR_MSG = "An error occurred.";

	private Game model;
	private PlayerDeckView view;

	@BeforeEach
	public void setUp() {
		model = EasyMock.createMock(Game.class);
		view = EasyMock.createMock(PlayerDeckView.class);
	}

	private void getCurrentPlayerHandIdsExpectation() {
		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andReturn(CURRENT_PLAYER_HAND_IDS);
	}

	private void getIsFaceUpExpectation(boolean isFaceUp) {
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
	}

	private void getCanDrawExpectation() {
		EasyMock.expect(model.getCanDraw()).andReturn(CAN_DRAW);
	}

	private void getPlayerNamesExpectation() {
		EasyMock.expect(model.getPlayerNames()).andReturn(PLAYER_NAMES);
	}

	private void getDeadIndicesExpectation() {
		EasyMock.expect(model.getDeadIndices()).andReturn(DEAD_INDICES);
	}


	private void getCurrentPlayerIndexExpectation() {
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(CURRENT_PLAYER_INDEX);
	}

	private void getIsGameOngoingExpectation(boolean isGameOngoing) {
		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
	}

	private void renderDrawPileExpectations() {
		getCanDrawExpectation();
		EasyMock.expect(model.isDrawPileEmpty()).andReturn(IS_DRAW_PILE_EMPTY);
	}

	private void renderTurnControlSectionExpectations(boolean canEndTurn) {
		EasyMock.expect(model.canPlaySelected()).andReturn(CAN_PLAY_SELECTED);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
	}

	private void buildAndRenderTurnControlSectionExpectations(
			boolean isGameOngoing, boolean canEndTurn) {
		getIsGameOngoingExpectation(isGameOngoing);
		renderTurnControlSectionExpectations(canEndTurn);
	}

	private void renderDiscardPileExpectations(
			boolean canDrawFromDiscard, String topDiscardId) {

		EasyMock.expect(model.canDrawFromDiscard()).andReturn(canDrawFromDiscard);
		EasyMock.expect(model.getTopDiscardId()).andReturn(topDiscardId);
	}

	private void expectRebuildHandCards(boolean isFaceUp) {
		getCurrentPlayerHandIdsExpectation();
		getIsFaceUpExpectation(isFaceUp);
		getCanDrawExpectation();

		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_DRAW);
		EasyMock.expectLastCall();
	}

	private void expectRebuildNameTags(boolean isGameOngoing) {
		getPlayerNamesExpectation();
		getDeadIndicesExpectation();
		getCurrentPlayerIndexExpectation();
		getIsGameOngoingExpectation(isGameOngoing);

		view.buildAddRenderPlayerNameTags(
				PLAYER_NAMES, CURRENT_PLAYER_INDEX, isGameOngoing, DEAD_INDICES);
		EasyMock.expectLastCall();
	}

	private void expectUpdateNameTags(boolean isGameOngoing) {
		getCurrentPlayerIndexExpectation();
		getDeadIndicesExpectation();
		getIsGameOngoingExpectation(isGameOngoing);

		view.renderPlayerNameTags(CURRENT_PLAYER_INDEX, isGameOngoing, DEAD_INDICES);
		EasyMock.expectLastCall();
	}

	private void expectUpdateDrawPile() {
		renderDrawPileExpectations();

		view.renderDrawPile(CAN_DRAW, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();
	}

	private void expectUpdateTurnControls(boolean canEndTurn) {
		renderTurnControlSectionExpectations(canEndTurn);

		view.renderTurnControlSection(CAN_PLAY_SELECTED, canEndTurn);
		EasyMock.expectLastCall();
	}

	private void expectRebuildTurnControls(boolean isGameOngoing, boolean canEndTurn) {
		buildAndRenderTurnControlSectionExpectations(isGameOngoing, canEndTurn);

		view.buildAndRenderTurnControlSection(
				isGameOngoing, CAN_PLAY_SELECTED, canEndTurn);
		EasyMock.expectLastCall();
	}

	private void expectUpdateDiscardPile(boolean canDrawFromDiscard, String topDiscardId) {
		renderDiscardPileExpectations(canDrawFromDiscard, topDiscardId);

		view.renderDiscardPile(canDrawFromDiscard, topDiscardId);
		EasyMock.expectLastCall();
	}

	private void expectRebindHandCards(boolean isFaceUp) {
		expectRebuildHandCards(isFaceUp);

		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();
	}

	private void expectRenderNextTurn(
			PlayerDeckController controller, int playerIndex, boolean canEndTurn) {

		getCurrentPlayerIndexExpectation();

		controller.handleChangeCurrentPlayer(playerIndex);
		EasyMock.expectLastCall();

		expectUpdateDrawPile();
		expectUpdateTurnControls(canEndTurn);
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
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(onError, controller);

		controller.setOnError(onError);

		controller.buildPlayerDeckScene();

		EasyMock.verify(onError, controller);
	}

	@Test
	public void buildDependentUI_called_success() {
		boolean isFaceUp = true;
		boolean isGameOngoing = true;

		expectRebuildHandCards(isFaceUp);
		expectRebuildNameTags(isGameOngoing);

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.buildDependentUI();

		EasyMock.verify(model, view);
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
		getCurrentPlayerIndexExpectation();

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_playerChanges_success() {
		int playerIndex = 1;
		boolean canPlaySelected = true;
		boolean canEndTurn = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		getCurrentPlayerIndexExpectation();

		controller.handleChangeCurrentPlayer(playerIndex);
		EasyMock.expectLastCall();

		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onNameTag_called_failed() {
		int playerIndex = 0;
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getCurrentPlayerIndex())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, onError);
	}

	@Test
	public void onNameTag_pendingTargetActionPresent_executesAction() {
		int playerIndex = 1;
		int initialPlayerIndex = 0;
		int newPlayerIndex = 2;
		boolean isGameOngoing = true;

		Consumer<Integer> mockAction = EasyMock.createMock(Consumer.class);
		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.pendingTargetAction = Optional.of(mockAction);

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(initialPlayerIndex);

		mockAction.accept(playerIndex);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(newPlayerIndex);
		EasyMock.expect(model.getIsGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(model.getDeadIndices()).andReturn(DEAD_INDICES);

		view.renderPlayerNameTags(newPlayerIndex, isGameOngoing, DEAD_INDICES);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, mockAction);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, view, mockAction);
		assertFalse(controller.pendingTargetAction.isPresent());
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_success() {
		int playerIndex = 0;
		boolean isFaceUp = true;
		boolean isGameOngoing = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		model.changeCurrentPlayerIndex(playerIndex);
		EasyMock.expectLastCall();

		model.setFaceUpToFalse();
		EasyMock.expectLastCall();

		expectUpdateNameTags(isGameOngoing);
		getIsFaceUpExpectation(isFaceUp);

		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, view, controller);
	}


	@Test
	public void rebindHandCards_called_success() {
		boolean isFaceUp = true;
		expectRebindHandCards(isFaceUp);

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.rebindHandCards();

		EasyMock.verify(model, view);
	}

	@Test
	public void onDrawPile_drawNonExplodingCard_rebindsHandAndUpdatesUI() {
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(model.drawFromPile()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.DEFUSE);

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		expectUpdateDrawPile();
		expectUpdateTurnControls(canEndTurn);

		EasyMock.replay(model, view, controller, drawnCard);

		controller.onDrawPile();

		EasyMock.verify(model, view, controller, drawnCard);
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
		EasyMock.expect(model.drawFromPile()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.EXPLODING_KITTEN);
		EasyMock.expect(drawnCard.getId()).andReturn(drawnCardId);
		EasyMock.expect(model.isDefusable()).andReturn(hasDefuse);
		EasyMock.expect(model.getDrawPileSize()).andReturn(drawPileSize);

		if (hasDefuse) {
			view.bindDefuseButton(EasyMock.anyObject());
		}
		else {
			view.bindExplodeButton(EasyMock.anyObject());
		}
		EasyMock.expectLastCall();

		int drawPileSizeAfterDraw = drawPileSize - 1;
		view.buildExplodeOverlay(hasDefuse, drawnCardId, drawPileSizeAfterDraw);
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
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
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

		model.toggleFaceUp();
		EasyMock.expectLastCall();

		getIsFaceUpExpectation(isFaceUp);

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
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
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

		getIsFaceUpExpectation(isFaceUp);
		model.toggleSelectedPlayerCardAt(handCardIndex);

		EasyMock.expectLastCall();
		expectUpdateTurnControls(canEndTurn);

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

		getIsFaceUpExpectation(isFaceUp);

		controller.onHandVisibilityButton();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void onPlayerHandCardButton_called_failed() {
		int handCardIndex = 0;
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getIsFaceUp())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, onError);
	}

	@Test
	public void onStartGameButton_called_success() {
		int startingPlayerIndex = 0;
		boolean canEndTurn = true;
		boolean isGameOngoing = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		model.startGame();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getStartingPlayerIndex()).andReturn(startingPlayerIndex);

		controller.handleChangeCurrentPlayer(startingPlayerIndex);
		EasyMock.expectLastCall();

		expectUpdateDrawPile();
		expectRebuildTurnControls(isGameOngoing, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onStartGameButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onStartGameButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.startGame();
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onStartGameButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onPlayCardsButton_godcatPlayed_overlayShown() {
		boolean canDrawFromDiscard = true;
		boolean canEndTurn = true;
		String playedCardId = "GODCAT_1";
		CardType playedCardType = CardType.GODCAT;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		EasyMock.expect(model.playSelectedCards()).andReturn(playedCardType);
		expectUpdateDiscardPile(canDrawFromDiscard, playedCardId);

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		expectUpdateTurnControls(canEndTurn);

		view.bindGodcatConfirmButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.buildGodcatOverlay(GameConstants.GODCAT_CARDTYPE_OPTIONS);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@ParameterizedTest
	@MethodSource("provideSkipAndSuperSkipTypeAndId")
	public void onPlayCardsButton_skipPlayed_updatedPlayer(
			String playedCardId, CardType playedCardType) {

		boolean canDrawFromDiscard = true;
		boolean canEndTurn = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.addMockedMethod("handleChangeCurrentPlayer")
				.addMockedMethod("updateByCardType")
				.createMock();

		EasyMock.expect(model.playSelectedCards()).andReturn(playedCardType);
		expectUpdateDiscardPile(canDrawFromDiscard, playedCardId);

		controller.rebindHandCards();
		EasyMock.expectLastCall();

		expectUpdateTurnControls(canEndTurn);

		controller.updateByCardType(playedCardType);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	private static Stream<Arguments> provideSkipAndSuperSkipTypeAndId() {
		return Stream.of(
				Arguments.of("SKIP_1", CardType.SKIP),
				Arguments.of("SUPERSKIP_1", CardType.SUPER_SKIP)
		);
	}

	private void expectTargetCardPlaySetup(PlayerDeckController controller, CardType cardType, String discardId, int currentPlayerIndex) {
		boolean canPlaySelected = true;
		boolean canEndTurn = true;

		EasyMock.expect(model.playSelectedCards()).andReturn(cardType);
		EasyMock.expect(model.canDrawFromDiscard()).andReturn(true);
		EasyMock.expect(model.getTopDiscardId()).andReturn(discardId);
		view.renderDiscardPile(true, discardId);
		EasyMock.expectLastCall();

		controller.rebindHandCards();
		EasyMock.expectLastCall();
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);
		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.getDeadIndices()).andReturn(DEAD_INDICES);
		view.renderPlayerNameTags(currentPlayerIndex, false, DEAD_INDICES);
		EasyMock.expectLastCall();
		view.renderTurnControlSection(false, false);
		EasyMock.expectLastCall();
	}

	@Test
	public void onPlayCardsButton_targetedAttackPlayed_targetSelectionEnabled() {
		int targetPlayerIndex = 2;

		PlayerDeckController controller = EasyMock.createMockBuilder(PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.addMockedMethod("handleChangeCurrentPlayer")
				.addMockedMethod("updateTurnControls")
				.createMock();

		expectTargetCardPlaySetup(controller, CardType.TARGETED_ATTACK, "TARGETED_ATTACK_1", 0);

		model.applyTargetedAttack(targetPlayerIndex);
		EasyMock.expectLastCall();

		controller.handleChangeCurrentPlayer(targetPlayerIndex);
		EasyMock.expectLastCall();

		controller.updateTurnControls();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		assertTrue(controller.pendingTargetAction.isPresent());
		controller.pendingTargetAction.get().accept(targetPlayerIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.playSelectedCards();
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onPlayCardsButton();

		EasyMock.verify(model, onError);
	}

	@ParameterizedTest
	@MethodSource("provideCardTypesWithNoAdditionalUIChange")
	public void updateByCardType_noAdditionalUIChange_success(CardType cardType) {
		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.updateByCardType(cardType);

		EasyMock.verify(model, view);
	}

	private static Stream<Arguments> provideCardTypesWithNoAdditionalUIChange() {
		return Stream.of(
				Arguments.of(CardType.ATTACK),
				Arguments.of(CardType.SHUFFLE),
				Arguments.of(CardType.CLONE),
				Arguments.of(CardType.SWAP_TOP_AND_BOTTOM),
				Arguments.of(CardType.DRAW_FROM_THE_BOTTOM),
				Arguments.of(CardType.WINNER_WINNER_CATNIP_DINNER),
				Arguments.of(CardType.RECYCLE),
				Arguments.of(CardType.DOUBLE_UP),
				Arguments.of(CardType.MILD_SHUFFLE)
		);
	}

	@ParameterizedTest
	@CsvSource({
			"SKIP",
			"SUPER_SKIP"
	})
	public void updateByCardType_skipOrSuperSkipPlayed_updateUI(CardType cardType) {
		boolean canEndTurn = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.updateByCardType(cardType);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void updateByCardType_seeTheFuturePlayed_updateUI() {
		EasyMock.expect(model.getSeeTheFutureCardIds()).andReturn(List.of());

		view.buildSeeTheFutureOverlay(List.of());
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.updateByCardType(CardType.SEE_THE_FUTURE);

		EasyMock.verify(model, view);
	}

	@Test
	public void updateByCardType_catomicBombPlayed_updateUI() {
		boolean canEndTurn = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.updateByCardType(CardType.CATOMIC_BOMB);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onEndTurnButton_called_success() {
		boolean canEndTurn = true;
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		model.advanceTurn();
		EasyMock.expectLastCall();

		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onEndTurnButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onEndTurnButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.advanceTurn();
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onEndTurnButton();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onDefuseButton_called_success() {
		boolean isFaceUp = true;
		boolean canEndTurn = true;
		int explodingKittenInsertIndex = 0;
		boolean canDrawFromDiscard = true;
		String topDiscardId = "DEFUSE_1";

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		EasyMock.expect(view.getExplodingKittenInsertIndex())
				.andReturn(explodingKittenInsertIndex);

		model.playDefuse(explodingKittenInsertIndex);
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		expectUpdateDiscardPile(canDrawFromDiscard, topDiscardId);
		expectRebindHandCards(isFaceUp);
		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);

		EasyMock.replay(model, view, controller);

		controller.onDefuseButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onDefuseButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		int explodingKittenInsertIndex = 0;

		EasyMock.expect(view.getExplodingKittenInsertIndex())
				.andReturn(explodingKittenInsertIndex);

		model.playDefuse(explodingKittenInsertIndex);
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onDefuseButton();

		EasyMock.verify(model, view, onError);
	}

	@Test
	public void onExplodeButton_gameOngoing_continueNextTurn() {
		boolean canEndTurn = true;
		boolean isGameOngoing = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		model.playExplode();
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		expectUpdateDrawPile();
		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);
		
		getIsGameOngoingExpectation(isGameOngoing);

		EasyMock.replay(model, view, controller);

		controller.onExplodeButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onExplodeButton_gameOver_showWinOverlay() {
		boolean canEndTurn = true;
		boolean isGameOngoing = false;
		String winnerName = "Audrey";

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		Runnable onRestart = EasyMock.createMock(Runnable.class);
		controller.setOnRestart(onRestart);

		model.playExplode();
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		expectUpdateDrawPile();
		expectRenderNextTurn(controller, CURRENT_PLAYER_INDEX, canEndTurn);

		getIsGameOngoingExpectation(isGameOngoing);

		EasyMock.expect(model.getWinnerName()).andReturn(winnerName);
		view.buildWinOverlay(winnerName);
		EasyMock.expectLastCall();

		view.bindPlayAgainButton(onRestart);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, onRestart);

		controller.onExplodeButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onExplodeButton_called_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.playExplode();
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onExplodeButton();

		EasyMock.verify(model, onError);
	}


	@Test
	public void onGodcatConfirm_validCardType_success() {
		CardType cardType = CardType.ATTACK;
		boolean canEndTurn = true;

		EasyMock.expect(view.getSelectedGodcatCardType()).andReturn(cardType);

		model.applyGodcat(cardType);
		EasyMock.expectLastCall();

		view.hideOverlay();
		EasyMock.expectLastCall();

		expectUpdateTurnControls(canEndTurn);

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(
				PlayerDeckController.class
				)
				.withConstructor(model, view)
				.addMockedMethod("updateByCardType")
				.createMock();

		controller.updateByCardType(cardType);

		EasyMock.replay(controller);

		controller.onGodcatConfirm();

		EasyMock.verify(view, controller);
	}

	@Test
	public void onGodcatConfirm_modelThrowsException_failed() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		CardType cardType = CardType.EXPLODING_KITTEN;

		EasyMock.expect(view.getSelectedGodcatCardType()).andReturn(cardType);

		model.applyGodcat(cardType);
		EasyMock.expectLastCall().andThrow(
				new RuntimeException(EXPECTED_ERROR_MSG));

		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);

		controller.onGodcatConfirm();

		EasyMock.verify(model, view, onError);
	}

}