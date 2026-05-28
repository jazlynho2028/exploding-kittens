package ui;

import domain.Game;
import domain.GameData;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final GameData model;

    private Consumer<String> onError;

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "View is injected by for compromise between MVC pattern and " +
                "testability, defensive copy is not applicable for JavaFX components"
    )
    public PlayerDeckController(Game model, PlayerDeckView view) {
        this.model = model;
        this.view = view;
        this.onError = message -> { };
    }

    public Scene buildPlayerDeckScene() {
        attempt(onError, this::buildDependentUI);
        bindUI();

        return view.createPlayerDeckScene();
    }

    void buildDependentUI() {
        rebuildHandCards();
        rebuildNameTags();
    }

    void rebuildHandCards() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHandIds(),
                model.getIsFaceUp(),
                model.getCanDraw()
        );
    }

    void rebindHandCards() {
        rebuildHandCards();
        bindHandCards();
    }

    private void bindHandCards() {
        view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
    }

    private void rebuildNameTags() {
        view.buildAddRenderPlayerNameTags(
                model.getPlayerNames(),
                model.getCurrentPlayerIndex(),
                model.getIsGameOngoing()
        );
    }

    void bindUI() {
        view.bindDrawPileButton(this::onDrawPile);
        view.bindHandVisibilityButton(this::onHandVisibilityButton);
        view.bindStartGameButton(this::onStartGameButton);
        view.bindNameTags(this::onNameTag);
        bindHandCards();
    }

    public void setOnError(Consumer<String> handler) {
        onError = handler;
    }

    void onNameTag(int playerIndex) {
        attempt(onError, () -> {
            if (model.getCurrentPlayerIndex() != playerIndex) {
                handleChangeCurrentPlayer(playerIndex);
            }
        });
    }

    void handleChangeCurrentPlayer(int playerIndex) {
        ((Game) model).changeCurrentPlayerIndex(playerIndex);
        ((Game) model).setFaceUpToFalse();

        updateNameTags();
        updateHandVisibilityButton();
        rebindHandCards();
    }

    private void updateNameTags() {
        view.renderPlayerNameTags(
                model.getCurrentPlayerIndex(),
                model.getIsGameOngoing()
        );
    }

    private void updateHandVisibilityButton() {
        view.renderHandVisibilityButton(model.getIsFaceUp());
    }

    void onDrawPile() {
        attempt(onError, () -> {
            ((Game) model).drawFromPile();

            view.renderDrawPile(
                    model.getCanDraw(),
                    model.isDrawPileEmpty()
            );
            rebindHandCards();
            view.renderTurnControlSection(
                    model.canPlaySelected(),
                    model.canEndTurn()
            );
        });
    }

    void onHandVisibilityButton() {
        attempt(onError, () -> {
            ((Game) model).setIsFaceUpToOpposite();

            updateHandVisibilityButton();
            rebindHandCards();
        });
    }

    void onPlayerHandCardButton(int handCardIndex) {
        attempt(onError, () -> {
            if (model.getIsFaceUp()) {
                ((Game) model).setIsSelectedOfPlayerCardAtIndexToOpposite(handCardIndex);

                updateTurnControls();
            }
            else {
                onHandVisibilityButton();
            }
        });
    }

    private void updateTurnControls() {
        view.renderTurnControlSection(
                model.canPlaySelected(),
                model.canEndTurn()
        );
    }

    void onStartGameButton() {
        attempt(onError, () -> {
            ((Game) model).startGame();

            handleChangeCurrentPlayer(model.getStartingPlayerIndex());

            updateDrawPile();
            rebuildTurnControl();
        });
    }

    private void updateDrawPile() {
        view.renderDrawPile(
                model.getCanDraw(),
                model.isDrawPileEmpty()
        );
    }

    private void rebuildTurnControl() {
        view.buildAndRenderTurnControlSection(
                model.getIsGameOngoing(),
                model.canPlaySelected(),
                model.canEndTurn()
        );
    }

}
