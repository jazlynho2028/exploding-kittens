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
    private final AssetProvider assets;

    private Consumer<String> onError;

    public PlayerDeckController(Game model, AssetProvider assets) {
        this.model = model;
        this.assets = assets;
        this.view = new PlayerDeckView(assets);
        this.onError = message -> { };

        buildAndBindUI();
    }

    // Fake constructor for tests to exclude UI view implementation
    PlayerDeckController(Game model, AssetProvider assets, PlayerDeckView view) {
        this.model = model;
        this.assets = assets;
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
        }
        catch (Exception e) {
            onError.accept(assets.getString("error.changePlayer"));
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
        }
        catch (Exception e) {
            onError.accept(assets.getString("error.drawFromPile"));
        }
    }

    void onHandVisibilityButton() {
        ((Game) model).setIsFaceUpToOpposite();

        view.renderHandVisibilityButton(model.getIsFaceUp());
        buildAddBindPlayerHandCards();
    }

    void onPlayerHandCardButton(int handCardIndex) {
        if (!model.getIsFaceUp()) {
            onHandVisibilityButton();
        }
        else {
            ((Game) model).setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);

            view.renderTurnControlSection(
                    model.canPlaySelected(),
                    model.canEndTurn()
            );
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
        }
        catch (Exception e) {
            onError.accept(assets.getString("error.startGame"));
        }
    }

    public Scene getPlayerDeckScene() {
        return view.createPlayerDeckScene();
    }

}
