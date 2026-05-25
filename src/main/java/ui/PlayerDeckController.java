package ui;

import domain.Game;
import domain.GameData;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.function.Consumer;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final GameData model;
    private final AssetProvider assets;

    private Consumer<String> onError;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View is injected by for compromise between MVC pattern and " +
                    "testability, defensive copy is not applicable for JavaFX components"
    )
    public PlayerDeckController(Game model, AssetProvider assets, PlayerDeckView view) {
        this.model = model;
        this.assets = assets;
        this.view = view;
        this.onError = message -> { };

        buildAndBindUI();
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    private void buildAndBindUI() {
        buildDependentUI();
        bindUI();
    }

    private void buildDependentUI() {
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
    }

    private void bindUI() {
        view.bindDrawPileButton(this::onDrawPile);
        view.bindHandVisibilityButton(this::onHandVisibilityButton);
        view.bindStartGameButton(this::onStartGameButton);
        view.bindNameTags(this::onNameTag);
        view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
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
        view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
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

}
