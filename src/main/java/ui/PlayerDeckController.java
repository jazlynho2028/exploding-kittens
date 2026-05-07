package ui;

import domain.GameState;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.function.Consumer;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final SceneManager sceneManager;
    private final GameState model;

    public PlayerDeckController(GameState model, SceneManager sceneManager, AssetManager assets) {
        this.model = model;
        this.sceneManager = sceneManager;
        this.view = new PlayerDeckView(assets);
        view.buildAndAddPlayerHandCards(
                this.model.getCurrentPlayerHand(),
                this.model.getIsFaceUp(),
                this.model.getIsBeforeDraw()
        );
        view.buildAddRenderPlayerNameTags(
                this.model.getPlayerNames(),
                this.model.getCurrentPlayerIndex(),
                this.model.isGameOngoing()
        );

        bindUI();
    }

    private void bindUI() {
        bindNameTags(this::onNameTag);
        bindDrawPile(this::onDrawPile);
        bindHandVisibilityButton(this::onHandVisibilityButton);
        bindPlayerHandCardButtons(this::onPlayerHandCardButton);
        bindStartGameButton(this::onStartGameButton);
    }

    private void bindNameTags(Consumer<Integer> handler) {
        ObservableList<Node> nameTagButtons = view.playerNamesContainer.getChildren();

        for (int i = 0; i < nameTagButtons.size(); i++) {
            int index = i;
            nameTagButtons.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
    }

    private void bindDrawPile(Runnable handler) {
        view.drawPileButton.setOnMouseClicked(e ->
                handler.run());
    }

    private void bindHandVisibilityButton(Runnable handler) {
        view.handVisibilityButton.setOnMouseClicked(e ->
                handler.run()
        );
    }

    private void bindPlayerHandCardButtons(Consumer<Integer> handler) {
        ObservableList<Node> handCards = view.handCardsContainer.getChildren();

        for (int i = 0; i < handCards.size(); i++) {
            int index = i;
            handCards.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
    }

    private void bindStartGameButton(Runnable handler) {
        view.startGameButton.setOnMouseClicked(e ->
                handler.run()
        );
    }

    private void onNameTag(int playerIndex) {
        if (model.getCurrentPlayerIndex() != playerIndex) {
            handleChangeCurrentPlayer(playerIndex);
        }
        System.out.println("NAME TAG CLICKED");
    }

    private void handleChangeCurrentPlayer(int playerIndex) {
        model.changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);

        view.renderPlayerNameTags(
                model.getCurrentPlayerIndex(),
                model.isGameOngoing()
        );
        view.renderHandVisibilityButton(model.getIsFaceUp());
        buildAddBindPlayerHandCards();
    }

    private void buildAddBindPlayerHandCards() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHand(),
                model.getIsFaceUp(),
                model.getIsBeforeDraw()
        );
        bindPlayerHandCardButtons(this::onPlayerHandCardButton);
    }

    private void onDrawPile() {
        model.drawFromPile();

        view.renderDrawPile(
                model.canDraw(),
                model.isDrawPileEmpty()
        );
        buildAddBindPlayerHandCards();
        view.renderTurnControlSection(
                model.canPlaySelected(),
                model.canEndTurn()
        );

        System.out.println("CARD DRAWN FROM PILE");
    }

    private void onHandVisibilityButton() {
        model.setIsFaceUpToOpposite();

        view.renderHandVisibilityButton(model.getIsFaceUp());
        buildAddBindPlayerHandCards();

        System.out.println("HAND VISIBILITY TOGGLE CLICKED");
    }

    private void onPlayerHandCardButton(int handCardIndex) {
        if (!model.getIsFaceUp()) {
            onFaceDownPlayerHandCardButton();
        }
        else {
            onFaceUpPlayerHandCardButton(handCardIndex);
            view.renderTurnControlSection(
                    model.canPlaySelected(),
                    model.canEndTurn()
            );
        }
    }

    private void onFaceDownPlayerHandCardButton() {
        onHandVisibilityButton();

        System.out.println("FACE DOWN HAND CARD BUTTON CLICKED");
    }

    private void onFaceUpPlayerHandCardButton(int handCardIndex) {
        String selectedCard = model.getCurrentPlayerHand().get(handCardIndex);

        if (view.isSelectedHandCard(handCardIndex)) {
            model.addToSelectedHandCards(selectedCard);

            System.out.println(selectedCard + " IN HAND SELECTED");
        }
        else {
            model.removeFromSelectedHandCards(selectedCard);

            System.out.println(selectedCard + " IN HAND DESELECTED");
        }
    }

    private void onStartGameButton() {
        model.startGame();

        handleChangeCurrentPlayer(model.getStartingPlayerIndex());

        view.renderDrawPile(
                model.canDraw(),
                model.isDrawPileEmpty()
        );
        view.buildAndRenderTurnControlSection(
                model.isGameOngoing(),
                model.canPlaySelected(),
                model.canEndTurn()
        );

        System.out.println("START GAME BUTTON CLICKED");
    }

    public Parent getViewRoot() {
        return view.getRoot();
    }

    public void handleError() {
        sceneManager.showErrorView();
    }
}
