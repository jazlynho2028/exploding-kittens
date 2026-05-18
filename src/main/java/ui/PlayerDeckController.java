package ui;

import domain.Game;
import domain.GameData;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.function.Consumer;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final GameData model;

    private Consumer<String> onError;

    public PlayerDeckController(Game model, AssetProvider assets) {
        this.model = model;
        this.view = new PlayerDeckView(assets);
        this.onError = message -> { };

        buildAndBindUI();
    }

    // Fake constructor for tests to exclude UI view implementation
    PlayerDeckController(Game model, PlayerDeckView view) {
        this.model = model;
        this.view = view;
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    private void buildAndBindUI() {
        view.buildAndAddPlayerHandCards(
                this.model.getCurrentPlayerHandIds(),
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
        view.drawPileButton.setOnMouseClicked(e -> onDrawPile());
        view.handVisibilityButton.setOnMouseClicked(e -> onHandVisibilityButton());
        bindPlayerHandCardButtons(this::onPlayerHandCardButton);
        view.startGameButton.setOnMouseClicked(e -> onStartGameButton());
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

    private void bindPlayerHandCardButtons(Consumer<Integer> handler) {
        ObservableList<Node> handCards = view.handCardsContainer.getChildren();

        for (int i = 0; i < handCards.size(); i++) {
            int index = i;
            handCards.get(i).setOnMouseClicked((e ->
                    handler.accept(index)
            ));
        }
    }

    void onNameTag(int playerIndex) {
        if (model.getCurrentPlayerIndex() != playerIndex) {
            handleChangeCurrentPlayer(playerIndex);
        }
        System.out.println("NAME TAG CLICKED");
    }

    void handleChangeCurrentPlayer(int playerIndex) {
        try {
            ((Game) model).changeCurrentPlayerIndexAndSetIsFaceUpToFalse(playerIndex);

            view.renderPlayerNameTags(
                    model.getCurrentPlayerIndex(),
                    model.isGameOngoing()
            );
            view.renderHandVisibilityButton(model.getIsFaceUp());
            buildAddBindPlayerHandCards();

            System.out.println("CURRENT PLAYER CHANGED");
        }
        catch (Exception e) {
            onError.accept("Failed to change current player.");
        }
    }

    void buildAddBindPlayerHandCards() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHandIds(),
                model.getIsFaceUp(),
                model.getIsBeforeDraw()
        );
        bindPlayerHandCardButtons(this::onPlayerHandCardButton);
    }

    void onDrawPile() {
        try {
            ((Game) model).drawFromPile();

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
        catch (Exception e) {
            onError.accept("Failed to draw from pile.");
        }
    }

    void onHandVisibilityButton() {
        ((Game) model).setIsFaceUpToOpposite();

        view.renderHandVisibilityButton(model.getIsFaceUp());
        buildAddBindPlayerHandCards();

        System.out.println("HAND VISIBILITY TOGGLE CLICKED");
    }

    void onPlayerHandCardButton(int handCardIndex) {
        if (!model.getIsFaceUp()) {
            onHandVisibilityButton();

            System.out.println("FACE DOWN HAND CARD BUTTON CLICKED");
        }
        else {
            ((Game) model).setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);

            view.renderTurnControlSection(
                    model.canPlaySelected(),
                    model.canEndTurn()
            );

            System.out.println("FACE UP HAND CARD BUTTON CLICKED");
        }
    }

    void onStartGameButton() {
        try {
            ((Game) model).startGame();

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
        catch (Exception e) {
            onError.accept("Failed to start game.");
        }
    }

    public Scene getPlayerDeckScene() {
        return view.createPlayerDeckScene();
    }

}
