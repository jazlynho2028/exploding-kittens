package ui;

import domain.Game;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final Game model;

    private Consumer<String> onError;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View and model are injected by for compromise between MVC pattern and " +
                    "testability, defensive copies are not applicable or not desired for JavaFX " +
                    "components and Game objects."
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

    void rebindHandCards() {
        rebuildHandCards();
        bindHandCards();
    }

    private void rebuildHandCards() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHandIds(),
                model.getIsFaceUp(),
                model.getCanDraw()
        );
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

    private void bindHandCards() {
        view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
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
        model.changeCurrentPlayerIndex(playerIndex);
        model.setFaceUpToFalse();

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
            model.drawFromPile();

            updateDrawPile();
            rebindHandCards();
            updateTurnControls();
        });
    }

    private void updateDrawPile() {
        view.renderDrawPile(
                model.getCanDraw(),
                model.isDrawPileEmpty()
        );
    }

    void onHandVisibilityButton() {
        attempt(onError, () -> {
            model.toggleFaceUp();

            updateHandVisibilityButton();
            rebindHandCards();
        });
    }

    void onPlayerHandCardButton(int handCardIndex) {
        attempt(onError, () -> {
            if (model.getIsFaceUp()) {
                model.toggleSelectedPlayerCardAt(handCardIndex);

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
            model.startGame();

            handleChangeCurrentPlayer(model.getStartingPlayerIndex());

            updateDrawPile();
            rebuildTurnControl();
        });
    }

    private void rebuildTurnControl() {
        view.buildAndRenderTurnControlSection(
                model.getIsGameOngoing(),
                model.canPlaySelected(),
                model.canEndTurn()
        );
    }

}