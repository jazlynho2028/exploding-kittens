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
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDeckControllerTests {

	private static final List<String> CURRENT_PLAYER_HAND_IDS = List.of();
	private static final List<String> PLAYER_NAMES = List.of();
	private static final boolean CAN_DRAW = true;
	private static final boolean CAN_PLAY = true;
	private static final int CURRENT_PLAYER_INDEX = 0;
	private static final boolean IS_DRAW_PILE_EMPTY = true;
	private static final boolean CAN_PLAY_SELECTED = true;
	private static final boolean CAN_END_TURN = true;
	private static final Set<Integer> ALIVE_INDICES = Set.of();
	private static final String TOP_DISCARD_ID = "DEFUSE_1";
	private static final String EXPECTED_ERROR_MSG = "An error occurred.";

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
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("buildDependentUI")
				.addMockedMethod("bindUI")
				.createMock();

		controller.buildDependentUI();
		EasyMock.expectLastCall();

		controller.bindUI();
		EasyMock.expectLastCall();

		EasyMock.expect(view.createPlayerDeckScene()).andStubReturn(expectedScene);

		EasyMock.replay(view, controller);

		Scene actualScene = controller.buildPlayerDeckScene();

		assertEquals(expectedScene, actualScene);
		EasyMock.verify(view, controller);
	}

	@Test
	public void buildPlayerDeckScene_buildDependentUIThrows_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
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

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void buildDependentUI_called_buildsHandCardsAndNameTags(boolean isGameOngoing) {
		boolean isFaceUp = true;

		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		EasyMock.expect(model.getCanPlay()).andStubReturn(CAN_PLAY);
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_DRAW);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getPlayerNames()).andStubReturn(PLAYER_NAMES);
		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(isGameOngoing);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		view.buildAddRenderPlayerNameTags(
				PLAYER_NAMES, CURRENT_PLAYER_INDEX, !isGameOngoing, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.buildDependentUI();

		EasyMock.verify(model, view);
	}

	@Test
	public void bindUI_called_bindsAllButtons() {
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
	public void onNameTag_samePlayerIndex_noChange() {
		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);

		EasyMock.replay(model);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onNameTag(CURRENT_PLAYER_INDEX);

		EasyMock.verify(model);
	}

	@Test
	public void onNameTag_differentPlayerIndex_changesPlayerAndRerenders() {
		int newPlayerIndex = 1;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		model.changeCurrentPlayerIndex(newPlayerIndex);
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onNameTag(newPlayerIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void onNameTag_pendingTargetActionPresent_executesActionOnly() {
		int newPlayerIndex = 1;

		Consumer<Integer> mockAction = EasyMock.createMock(Consumer.class);

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		controller.pendingTargetAction = Optional.of(mockAction);

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);

		mockAction.accept(newPlayerIndex);
		EasyMock.expectLastCall();

		controller.updateAll();

		EasyMock.replay(model, mockAction, controller);

		controller.onNameTag(newPlayerIndex);

		EasyMock.verify(model, mockAction, controller);
		assertFalse(controller.pendingTargetAction.isPresent());
	}

	@Test
	public void onNameTag_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getCurrentPlayerIndex())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));
		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onNameTag(CURRENT_PLAYER_INDEX);

		EasyMock.verify(model, onError);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void updateAll_called_updatesAllViewComponents(boolean isGameOngoing) {
		boolean isFaceUp = false;

		PlayerDeckView strictView = EasyMock.createStrictMock(PlayerDeckView.class);

		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		EasyMock.expect(model.getCanPlay()).andStubReturn(CAN_PLAY);
		strictView.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_DRAW);
		EasyMock.expectLastCall();
		strictView.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.expect(model.canPlaySelected()).andStubReturn(CAN_PLAY_SELECTED);
		EasyMock.expect(model.canEndTurn()).andStubReturn(CAN_END_TURN);
		strictView.renderTurnControlSection(CAN_PLAY_SELECTED, CAN_END_TURN);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(isGameOngoing);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		strictView.renderPlayerNameTags
				(CURRENT_PLAYER_INDEX, !isGameOngoing, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCanDraw()).andStubReturn(CAN_DRAW);
		EasyMock.expect(model.isDrawPileEmpty()).andStubReturn(IS_DRAW_PILE_EMPTY);
		strictView.renderDrawPile(CAN_DRAW, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getTopDiscardId()).andStubReturn(TOP_DISCARD_ID);
		strictView.renderDiscardPile(TOP_DISCARD_ID);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		strictView.renderHandVisibilityButton(isFaceUp, true);
		EasyMock.expectLastCall();

		EasyMock.replay(model, strictView);

		PlayerDeckController controller = new PlayerDeckController(model, strictView);
		controller.updateAll();

		EasyMock.verify(model, strictView);
	}

	@Test
	public void rebindHandCards_called_rebuildsAndBindsHandCards() {
		boolean isFaceUp = true;

		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		EasyMock.expect(model.getCanPlay()).andStubReturn(CAN_PLAY);
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, CAN_DRAW);
		EasyMock.expectLastCall();
		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.rebindHandCards();

		EasyMock.verify(model, view);
	}

	@Test
	public void onDrawPile_nonExplodingCardDrawn_callsUpdateAll() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(model.drawFromPile()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.DEFUSE);

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, drawnCard);

		controller.onDrawPile();

		EasyMock.verify(model, view, controller, drawnCard);
	}

	@ParameterizedTest
	@CsvSource({
			"true",
			"false"
	})
	public void onDrawPile_explodingKittenDrawn_buildsExplodeOverlay(boolean isDefusable) {
		String drawnCardId = "EXPLODINGKITTEN_1";
		int drawPileSize = 2;

		Card drawnCard = EasyMock.createMock(Card.class);
		EasyMock.expect(model.drawFromPile()).andStubReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andStubReturn(CardType.EXPLODING_KITTEN);
		EasyMock.expect(drawnCard.getId()).andStubReturn(drawnCardId);
		EasyMock.expect(model.isDefusable()).andStubReturn(isDefusable);
		EasyMock.expect(model.getDrawPileSize()).andStubReturn(drawPileSize);

		if (isDefusable) {
			view.bindDefuseButton(EasyMock.anyObject());
		}
		else {
			view.bindExplodeButton(EasyMock.anyObject());
		}
		EasyMock.expectLastCall();

		view.buildExplodeOverlay(isDefusable, drawnCardId, drawPileSize - 1);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, drawnCard);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onDrawPile();

		EasyMock.verify(model, view, drawnCard);
	}

	@Test
	public void onDrawPile_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.drawFromPile())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));
		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onDrawPile();

		EasyMock.verify(model, onError);
	}

	@Test
	public void onHandVisibilityButton_called_togglesAndRebinds() {
		boolean isFaceUp = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("rebindHandCards")
				.createMock();

		model.toggleFaceUp();
		EasyMock.expectLastCall();
		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		view.renderHandVisibilityButton(isFaceUp, true);
		EasyMock.expectLastCall();
		controller.rebindHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onHandVisibilityButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onHandVisibilityButton_modelThrowsException_callsOnError() {
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
	public void onPlayerHandCardButton_cardsFaceUp_togglesSelectionAndUpdatesTurnControls() {
		int handCardIndex = 2;
		boolean isFaceUp = true;

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		model.toggleSelectedPlayerCardAt(handCardIndex);
		EasyMock.expectLastCall();
		EasyMock.expect(model.canPlaySelected()).andStubReturn(CAN_PLAY_SELECTED);
		EasyMock.expect(model.canEndTurn()).andStubReturn(CAN_END_TURN);
		view.renderTurnControlSection(CAN_PLAY_SELECTED, CAN_END_TURN);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, view);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceDown_delegatesToHandVisibilityButton() {
		int handCardIndex = 0;
		boolean isFaceUp = false;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("onHandVisibilityButton")
				.createMock();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		controller.onHandVisibilityButton();
		EasyMock.expectLastCall();

		EasyMock.replay(model, controller);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, controller);
	}

	@Test
	public void onPlayerHandCardButton_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getIsFaceUp())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));
		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onPlayerHandCardButton(0);

		EasyMock.verify(model, onError);
	}

	@Test
	public void onStartGameButton_called_startsGameAndRendersUI() {
		boolean isGameOngoing = true;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		model.startGame();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(isGameOngoing);
		EasyMock.expect(model.canPlaySelected()).andStubReturn(CAN_PLAY_SELECTED);
		EasyMock.expect(model.canEndTurn()).andStubReturn(CAN_END_TURN);
		view.buildAndRenderTurnControlSection(
				isGameOngoing, CAN_PLAY_SELECTED, CAN_END_TURN);
		EasyMock.expectLastCall();

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onStartGameButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onStartGameButton_modelThrowsException_callsOnError() {
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
	public void onPlayCardsButton_godcatPlayed_showsGodcatOverlay() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(model.getSelectedCardsCount()).andStubReturn(1);
		EasyMock.expect(model.playSelectedCards()).andStubReturn(CardType.GODCAT);

		controller.updateAll();
		EasyMock.expectLastCall();

		view.bindCardSelectConfirmButton(EasyMock.anyObject());
		EasyMock.expectLastCall();
		view.buildCardSelectOverlay(
				EasyMock.eq(GameConstants.SELECTABLE_CARDTYPE_OPTIONS),
				EasyMock.anyString()
		);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_skipPlayed_delegatesToUpdateByCardType() {
		CardType cardType = CardType.SKIP;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.addMockedMethod("updateByCardType")
				.createMock();

		EasyMock.expect(model.getSelectedCardsCount()).andStubReturn(1);
		EasyMock.expect(model.playSelectedCards()).andStubReturn(cardType);

		controller.updateAll();
		EasyMock.expectLastCall();
		controller.updateByCardType(cardType);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		EasyMock.expect(model.getSelectedCardsCount()).andStubReturn(1);

		EasyMock.expect(model.playSelectedCards())
				.andThrow(new RuntimeException(EXPECTED_ERROR_MSG));
		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onPlayCardsButton();

		EasyMock.verify(model, onError);
	}

	@ParameterizedTest
	@MethodSource("provideCatCardTypes")
	public void onPlayCardsButton_allCatCardTypesTwoOfAKind_enablesPlayerSelectMode(CardType catCardType) {
		boolean isFaceUp = true;
		int targetIndex = 1;

		PlayerDeckController controller = EasyMock.createMockBuilder(PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(model.getSelectedCardsCount()).andReturn(GameConstants.TWO_CARDS);
		EasyMock.expect(model.playSelectedCards()).andStubReturn(catCardType);

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		view.renderPlayerNameTags(CURRENT_PLAYER_INDEX, true, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.expect(model.isDrawPileEmpty()).andStubReturn(IS_DRAW_PILE_EMPTY);
		view.renderDrawPile(false, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		view.renderHandVisibilityButton(isFaceUp, false);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerHandIds()).andStubReturn(CURRENT_PLAYER_HAND_IDS);
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, false);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(false, false);
		EasyMock.expectLastCall();

		model.applyTwoOfAKind(EasyMock.eq(targetIndex), EasyMock.anyObject(java.util.Random.class));
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();
		controller.pendingTargetAction.get().accept(targetIndex);

		EasyMock.verify(model, view, controller);
	}

	private static Stream<Arguments> provideCatCardTypes() {
		return Stream.of(
				Arguments.of(CardType.CAT_CARD_1),
				Arguments.of(CardType.CAT_CARD_2),
				Arguments.of(CardType.CAT_CARD_3),
				Arguments.of(CardType.CAT_CARD_4)
		);
	}

	@Test
	public void onPlayCardsButton_threeOfAKind_showsCardSelectOverlay() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(model.getSelectedCardsCount()).andReturn(GameConstants.THREE_CARDS);
		EasyMock.expect(model.playSelectedCards()).andStubReturn(CardType.CAT_CARD_2);

		controller.updateAll();
		EasyMock.expectLastCall();

		view.bindCardSelectConfirmButton(EasyMock.anyObject());
		EasyMock.expectLastCall();
		view.buildCardSelectOverlay(
				EasyMock.eq(GameConstants.SELECTABLE_CARDTYPE_OPTIONS),
				EasyMock.eq("REQUEST")
		);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayCardsButton_invalidCatCardComboSize_fallsThroughSafely() {
		PlayerDeckController controller = EasyMock.createMockBuilder(PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(model.getSelectedCardsCount()).andReturn(1);
		EasyMock.expect(model.playSelectedCards()).andStubReturn(CardType.CAT_CARD_1);

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onPlayCardsButton();

		assertFalse(controller.pendingTargetAction.isPresent());
		EasyMock.verify(model, view, controller);
	}

	@ParameterizedTest
	@MethodSource("provideCardTypesWithNoAdditionalUIChange")
	public void updateByCardType_noAdditionalUIChange_noViewInteractions(CardType cardType) {
		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.updateByCardType(cardType);

		EasyMock.verify(model, view);
	}

	private static Stream<Arguments> provideCardTypesWithNoAdditionalUIChange() {
		return Stream.of(
				Arguments.of(CardType.ATTACK),
				Arguments.of(CardType.SHUFFLE),
				Arguments.of(CardType.SKIP),
				Arguments.of(CardType.CATOMIC_BOMB),
				Arguments.of(CardType.SUPER_SKIP),
				Arguments.of(CardType.CLONE),
				Arguments.of(CardType.SWAP_TOP_AND_BOTTOM),
				Arguments.of(CardType.WINNER_WINNER_CATNIP_DINNER),
				Arguments.of(CardType.DOUBLE_UP),
				Arguments.of(CardType.MILD_SHUFFLE)
		);
	}

	@Test
	public void updateByCardType_seeTheFuturePlayed_buildsOverlay() {
		List<String> futureCardIds = List.of("ATTACK_1", "SKIP_2", "DEFUSE_3");

		EasyMock.expect(model.getSeeTheFutureCardIds()).andStubReturn(futureCardIds);
		view.buildSeeTheFutureOverlay(futureCardIds);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.updateByCardType(CardType.SEE_THE_FUTURE);

		EasyMock.verify(model, view);
	}

	@Test
	public void updateByCardType_targetedAttackPlayed_enablesPlayerSelectMode() {
		boolean isFaceUp = false;

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		view.renderPlayerNameTags(CURRENT_PLAYER_INDEX, true, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.expect(model.isDrawPileEmpty()).andStubReturn(IS_DRAW_PILE_EMPTY);
		view.renderDrawPile(false, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		view.renderHandVisibilityButton(isFaceUp, false);
		EasyMock.expectLastCall();
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, false);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(false, false);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.updateByCardType(CardType.TARGETED_ATTACK);

		EasyMock.verify(model, view);
		assertTrue(controller.pendingTargetAction.isPresent());
	}

	@Test
	public void updateByCardType_ragebaitPlayed_enablesPlayerSelectMode() {
		boolean isFaceUp = false;

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		view.renderPlayerNameTags(CURRENT_PLAYER_INDEX, true, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.expect(model.isDrawPileEmpty()).andStubReturn(IS_DRAW_PILE_EMPTY);
		view.renderDrawPile(false, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		view.renderHandVisibilityButton(isFaceUp, false);
		EasyMock.expectLastCall();
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, false);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(false, false);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.updateByCardType(CardType.RAGEBAIT);

		EasyMock.verify(model, view);
		assertTrue(controller.pendingTargetAction.isPresent());
	}

	@Test
	public void updateByCardType_recyclePlayed_nonExplodingCard_success() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		Card drawnCard = EasyMock.createMock(Card.class);

		EasyMock.expect(model.drawRecycle()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.SKIP);

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, drawnCard);

		controller.updateByCardType(CardType.RECYCLE);

		EasyMock.verify(model, view, controller, drawnCard);
	}

	@Test
	public void updateByCardType_recyclePlayed_ExplodingCard_success() {
		String drawnCardId = "EXPLODINGKITTEN_1";
		int drawPileSize = 0;

		Card drawnCard = EasyMock.createMock(Card.class);

		EasyMock.expect(model.drawRecycle()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.EXPLODING_KITTEN);
		EasyMock.expect(drawnCard.getId()).andReturn(drawnCardId);
		EasyMock.expect(model.isDefusable()).andReturn(false);
		EasyMock.expect(model.getDrawPileSize()).andReturn(drawPileSize);

		view.bindExplodeButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.buildExplodeOverlay(false, drawnCardId, drawPileSize - 1);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, drawnCard);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.updateByCardType(CardType.RECYCLE);

		EasyMock.verify(model, view, drawnCard);
	}

	@Test
	public void updateByCardType_drawFromTheBottomPlayed_nonExplodingCard_success() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		Card drawnCard = EasyMock.createMock(Card.class);

		EasyMock.expect(model.drawFromTheBottom()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.SKIP);

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, drawnCard);

		controller.updateByCardType(CardType.DRAW_FROM_THE_BOTTOM);

		EasyMock.verify(model, view, controller, drawnCard);
	}

	@Test
	public void updateByCardType_drawFromTheBottomPlayed_explodingCard_success() {
		String drawnCardId = "EXPLODINGKITTEN_1";
		int drawPileSize = 2;

		Card drawnCard = EasyMock.createMock(Card.class);

		EasyMock.expect(model.drawFromTheBottom()).andReturn(drawnCard);
		EasyMock.expect(drawnCard.getType()).andReturn(CardType.EXPLODING_KITTEN);
		EasyMock.expect(drawnCard.getId()).andReturn(drawnCardId);
		EasyMock.expect(model.isDefusable()).andReturn(false);
		EasyMock.expect(model.getDrawPileSize()).andReturn(drawPileSize);

		view.bindExplodeButton(EasyMock.anyObject());
		EasyMock.expectLastCall();

		view.buildExplodeOverlay(false, drawnCardId, drawPileSize - 1);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, drawnCard);

		PlayerDeckController controller = new PlayerDeckController(model, view);

		controller.updateByCardType(CardType.DRAW_FROM_THE_BOTTOM);

		EasyMock.verify(model, view, drawnCard);
	}

	@Test
	public void onEndTurnButton_called_advancesTurnAndRendersNext() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		model.endTurn();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(true);

		EasyMock.replay(model, view, controller);

		controller.onEndTurnButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onEndTurnButton_gameOver_showsWinOverlay() {
		String winnerName = "Audrey";

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		Runnable onRestart = EasyMock.createMock(Runnable.class);
		controller.setOnRestart(onRestart);

		model.endTurn();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(false);
		EasyMock.expect(model.getWinnerName()).andStubReturn(winnerName);
		view.buildWinOverlay(winnerName);
		EasyMock.expectLastCall();
		view.bindPlayAgainButton(onRestart);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, onRestart);

		controller.onEndTurnButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onEndTurnButton_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		model.endTurn();
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
	public void onDefuseButton_called_defusesAndAdvancesTurn() {
		int insertIndex = 2;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(view.getExplodingKittenInsertIndex()).andStubReturn(insertIndex);
		model.playDefuse(insertIndex);
		EasyMock.expectLastCall();
		view.hideOverlay();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onDefuseButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onDefuseButton_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		int insertIndex = 0;

		EasyMock.expect(view.getExplodingKittenInsertIndex()).andStubReturn(insertIndex);
		model.playDefuse(insertIndex);
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
	public void onExplodeButton_gameOngoing_advancesToNextTurn() {
		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		model.playExplode();
		EasyMock.expectLastCall();
		view.hideOverlay();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(true);

		EasyMock.replay(model, view, controller);

		controller.onExplodeButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onExplodeButton_gameOver_showsWinOverlay() {
		String winnerName = "Audrey";

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		Runnable onRestart = EasyMock.createMock(Runnable.class);
		controller.setOnRestart(onRestart);

		model.playExplode();
		EasyMock.expectLastCall();
		view.hideOverlay();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();
		EasyMock.expect(model.getIsGameOngoing()).andStubReturn(false);
		EasyMock.expect(model.getWinnerName()).andStubReturn(winnerName);
		view.buildWinOverlay(winnerName);
		EasyMock.expectLastCall();
		view.bindPlayAgainButton(onRestart);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller, onRestart);

		controller.onExplodeButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onExplodeButton_modelThrowsException_callsOnError() {
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
	public void onGodcatConfirm_validCardType_appliesGodcatAndRendersAll() {
		CardType cardType = CardType.ATTACK;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.addMockedMethod("updateByCardType")
				.createMock();

		EasyMock.expect(view.getSelectedOverlayCardType()).andStubReturn(cardType);
		model.applyGodcat(cardType);
		EasyMock.expectLastCall();
		view.hideOverlay();
		EasyMock.expectLastCall();
		controller.updateAll();
		EasyMock.expectLastCall();
		controller.updateByCardType(cardType);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, controller);

		controller.onGodcatConfirm();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onGodcatConfirm_modelThrowsException_callsOnError() {
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		CardType cardType = CardType.ATTACK;

		EasyMock.expect(view.getSelectedOverlayCardType()).andStubReturn(cardType);
		model.applyGodcat(cardType);
		EasyMock.expectLastCall().andThrow(new RuntimeException(EXPECTED_ERROR_MSG));
		onError.accept(EXPECTED_ERROR_MSG);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, view);
		controller.setOnError(onError);
		controller.onGodcatConfirm();

		EasyMock.verify(model, view, onError);
	}

	@Test
	public void onCardRequestConfirm_called_setsPendingActionAndEnablesSelect() {
		CardType chosenType = CardType.DEFUSE;
		boolean isFaceUp = true;
		int targetIndex = 1;

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class)
				.withConstructor(model, view)
				.addMockedMethod("updateAll")
				.createMock();

		EasyMock.expect(view.getSelectedOverlayCardType()).andReturn(chosenType);
		view.hideOverlay();
		EasyMock.expectLastCall();

		controller.updateAll();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerIndex()).andStubReturn(CURRENT_PLAYER_INDEX);
		EasyMock.expect(model.getAliveIndices()).andStubReturn(ALIVE_INDICES);
		view.renderPlayerNameTags(CURRENT_PLAYER_INDEX, true, ALIVE_INDICES);
		EasyMock.expectLastCall();

		EasyMock.expect(model.isDrawPileEmpty()).andStubReturn(IS_DRAW_PILE_EMPTY);
		view.renderDrawPile(false, IS_DRAW_PILE_EMPTY);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andStubReturn(isFaceUp);
		view.renderHandVisibilityButton(isFaceUp, false);
		EasyMock.expectLastCall();

		EasyMock.expect(model.getCurrentPlayerHandIds())
				.andStubReturn(CURRENT_PLAYER_HAND_IDS);
		view.buildAndAddPlayerHandCards(CURRENT_PLAYER_HAND_IDS, isFaceUp, false);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(false, false);
		EasyMock.expectLastCall();

		model.applyThreeOfAKind(targetIndex, chosenType);
		EasyMock.expectLastCall().once();

		EasyMock.replay(model, view, controller);

		assertFalse(controller.pendingTargetAction.isPresent());

		controller.onCardRequestConfirm();

		assertTrue(controller.pendingTargetAction.isPresent());

		controller.pendingTargetAction.get().accept(targetIndex);

		EasyMock.verify(model, view, controller);
	}
}