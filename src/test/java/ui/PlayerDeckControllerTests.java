package ui;

import domain.Game;
import domain.GameException;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayerDeckControllerTests {

	private ArrayList<String> currentPlayerHandIds;
	private boolean isFaceUp;
	private boolean isBeforeDraw;
	private ArrayList<String> playerNames;
	private int currentPlayerIndex;
	private boolean isGameOngoing;

	boolean canDraw;
	boolean isDrawPileEmpty;
	boolean canPlaySelected;
	boolean canEndTurn;
	int startingPlayerIndex;

	private Game model;
	private AssetProvider assets;
	private PlayerDeckView view;

	public void setUpBeforeGame() {
		currentPlayerHandIds = new ArrayList<>();
		isFaceUp = false;
		isBeforeDraw = true;
		playerNames = new ArrayList<>();
		currentPlayerIndex = 0;
		isGameOngoing = false;

		model = EasyMock.createMock(Game.class);
		assets = EasyMock.createMock(AssetProvider.class);
		view = EasyMock.createMock(PlayerDeckView.class);

		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(currentPlayerHandIds);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.getIsBeforeDraw()).andReturn(isBeforeDraw);
		EasyMock.expect(model.getPlayerNames()).andReturn(playerNames);
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.isGameOngoing()).andReturn(isGameOngoing);

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, isBeforeDraw);
		EasyMock.expectLastCall();
		view.buildAddRenderPlayerNameTags(playerNames, currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();
		view.bindActionButtons(
				EasyMock.anyObject(Runnable.class),
				EasyMock.anyObject(Runnable.class),
				EasyMock.anyObject(Runnable.class)
		);
		EasyMock.expectLastCall();
		view.bindNameTags(EasyMock.anyObject(Consumer.class));
		EasyMock.expectLastCall();
		view.bindPlayerHandCardButtons(EasyMock.anyObject(Consumer.class));
		EasyMock.expectLastCall();
	}

	@Test
	public void constructor_called_success() {
		setUpBeforeGame();
		EasyMock.replay(model, view);

		new PlayerDeckController(model, assets, view);

		EasyMock.verify(model, view);
	}

	@Test
	public void onNameTag_playerStaysTheSame_noChange() {
		setUpBeforeGame();
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		int playerIndex = 0;
		controller.onNameTag(playerIndex);

		EasyMock.verify(model, view);
	}

	@Test
	public void onNameTag_playerChanges_success() {
		setUpBeforeGame();
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(PlayerDeckController.class)
				.withConstructor(model, assets, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();
		int playerIndex = 1;

		controller.handleChangeCurrentPlayer(playerIndex);
		EasyMock.expectLastCall();
		EasyMock.replay(controller);

		controller.onNameTag(playerIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_success() {
		setUpBeforeGame();
		int playerIndex = 0;
		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall();
		EasyMock.expect(model.getCurrentPlayerIndex()).andReturn(currentPlayerIndex);
		EasyMock.expect(model.isGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		view.renderPlayerNameTags(currentPlayerIndex, isGameOngoing);
		EasyMock.expectLastCall();
		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(PlayerDeckController.class)
				.withConstructor(model, assets, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();

		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();
		EasyMock.replay(controller);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void handleChangeCurrentPlayer_playerChanges_fail() {
		setUpBeforeGame();
		Consumer<String> onError = EasyMock.createMock(Consumer.class);
		int playerIndex = 0;

		String expectedMsg = "Failed to change current player.";
		EasyMock.expect(assets.getString("error.changePlayer")).andReturn(
				expectedMsg
		);

		model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);
		EasyMock.expectLastCall().andThrow(new GameException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.handleChangeCurrentPlayer(playerIndex);

		EasyMock.verify(model, assets, view, onError);
	}

	@Test
	public void buildAddBindPlayerHandCards_called_success() {
		setUpBeforeGame();
		EasyMock.expect(model.getCurrentPlayerHandIds()).andReturn(currentPlayerHandIds);
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.getIsBeforeDraw()).andReturn(isBeforeDraw);

		view.buildAndAddPlayerHandCards(currentPlayerHandIds, isFaceUp, isBeforeDraw);
		EasyMock.expectLastCall();
		view.bindPlayerHandCardButtons(EasyMock.anyObject());
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);

		controller.buildAddBindPlayerHandCards();

		EasyMock.verify(model, view);
	}

	@Test
	public void onDrawPile_drawsCard_success() {
		setUpBeforeGame();

		canDraw = true;
		isDrawPileEmpty = true;
		canPlaySelected = true;
		canEndTurn = true;

		EasyMock.expect(model.canDraw()).andReturn(canDraw);
		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);

		model.drawFromPile();
		EasyMock.expectLastCall();

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();
		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();

		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(controller);

		controller.onDrawPile();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onDrawPile_drawsCard_fail() {
		setUpBeforeGame();
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		String expectedMsg = "Failed to draw from pile.";
		EasyMock.expect(assets.getString("error.drawFromPile")).andReturn(
				expectedMsg
		);

		model.drawFromPile();
		EasyMock.expectLastCall().andThrow(new GameException(expectedMsg));
		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, view, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.onDrawPile();

		EasyMock.verify(model, assets, view, onError);
	}

	@Test
	public void onHandVisibilityButton_called_success() {
		setUpBeforeGame();

		model.setIsFaceUpToOpposite();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		view.renderHandVisibilityButton(isFaceUp);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("buildAddBindPlayerHandCards")
				.createMock();

		controller.buildAddBindPlayerHandCards();
		EasyMock.expectLastCall();

		EasyMock.replay(controller);

		controller.onHandVisibilityButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceDown_callsOnHandVisibility() {
		setUpBeforeGame();

		isFaceUp = false;
		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("onHandVisibilityButton")
				.createMock();
		int handCardIndex = 0;

		controller.onHandVisibilityButton();
		EasyMock.expectLastCall();

		EasyMock.replay(controller);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onPlayerHandCardButton_cardsFaceUp_callsModelMethod() {
		setUpBeforeGame();
		int handCardIndex = 0;

		boolean isFaceUp = true;
		boolean canPlaySelected = false;
		boolean canEndTurn = false;

		EasyMock.expect(model.getIsFaceUp()).andReturn(isFaceUp);
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);

		model.setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);
		EasyMock.expectLastCall();

		view.renderTurnControlSection(canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);

		controller.onPlayerHandCardButton(handCardIndex);

		EasyMock.verify(model, view);
	}

	@Test
	public void onStartGameButton_called_success() {
		setUpBeforeGame();

		model.startGame();
		EasyMock.expectLastCall();

		EasyMock.expect(model.getStartingPlayerIndex()).andReturn(startingPlayerIndex);
		EasyMock.expect(model.canDraw()).andReturn(canDraw);
		EasyMock.expect(model.isDrawPileEmpty()).andReturn(isDrawPileEmpty);
		EasyMock.expect(model.isGameOngoing()).andReturn(isGameOngoing);
		EasyMock.expect(model.canPlaySelected()).andReturn(canPlaySelected);
		EasyMock.expect(model.canEndTurn()).andReturn(canEndTurn);

		view.renderDrawPile(canDraw, isDrawPileEmpty);
		EasyMock.expectLastCall();

		view.buildAndRenderTurnControlSection(isGameOngoing, canPlaySelected, canEndTurn);
		EasyMock.expectLastCall();

		EasyMock.replay(model, view);

		PlayerDeckController controller = EasyMock.createMockBuilder(
						PlayerDeckController.class
				)
				.withConstructor(model, assets, view)
				.addMockedMethod("handleChangeCurrentPlayer")
				.createMock();

		controller.handleChangeCurrentPlayer(startingPlayerIndex);
		EasyMock.expectLastCall();

		EasyMock.replay(controller);

		controller.onStartGameButton();

		EasyMock.verify(model, view, controller);
	}

	@Test
	public void onStartGameButton_called_fail() {
		setUpBeforeGame();
		Consumer<String> onError = EasyMock.createMock(Consumer.class);

		String expectedMsg = "Failed to start game.";
		EasyMock.expect(assets.getString("error.startGame")).andReturn(
				expectedMsg
		);

		model.startGame();
		EasyMock.expectLastCall().andThrow(new GameException(expectedMsg));

		onError.accept(expectedMsg);
		EasyMock.expectLastCall();

		EasyMock.replay(model, assets, onError);

		PlayerDeckController controller = new PlayerDeckController(model, assets, view);
		controller.setOnError(onError);

		controller.onStartGameButton();

		EasyMock.verify(model, assets, onError);
	}
}