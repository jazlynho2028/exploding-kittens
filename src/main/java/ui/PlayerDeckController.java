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
        attempt(onError, () -> {
            buildDependentUI();
            bindUI();
        });

        return view.createPlayerDeckScene();
    }

    private void buildDependentUI() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHandIds(),
                model.getIsFaceUp(),
                model.getCanDraw()
        );
        view.buildAddRenderPlayerNameTags(
                model.getPlayerNames(),
                model.getCurrentPlayerIndex(),
                model.getIsGameOngoing()
        );
    }

    private void bindUI() {
        view.bindDrawPileButton(this::onDrawPile);
        view.bindHandVisibilityButton(this::onHandVisibilityButton);
        view.bindStartGameButton(this::onStartGameButton);
        view.bindNameTags(this::onNameTag);
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
        ((Game) model).changeCurrentPlayerIndex(playerIndex);
        ((Game) model).setFaceUpToFalse();

        view.renderPlayerNameTags(
                model.getCurrentPlayerIndex(),
                model.getIsGameOngoing()
        );
        view.renderHandVisibilityButton(model.getIsFaceUp());
        buildAddBindPlayerHandCards();
    }

    void buildAddBindPlayerHandCards() {
        view.buildAndAddPlayerHandCards(
                model.getCurrentPlayerHandIds(),
                model.getIsFaceUp(),
                model.getCanDraw()
        );
        view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
    }

    void onDrawPile() {
        attempt(onError, () -> {
            ((Game) model).drawFromPile();

            view.renderDrawPile(
                    model.getCanDraw(),
                    model.isDrawPileEmpty()
            );
            buildAddBindPlayerHandCards();
            view.renderTurnControlSection(
                    model.canPlaySelected(),
                    model.canEndTurn()
            );
        });
    }

    void onHandVisibilityButton() {
        attempt(onError, () -> {
            ((Game) model).setIsFaceUpToOpposite();

            view.renderHandVisibilityButton(model.getIsFaceUp());
            buildAddBindPlayerHandCards();
        });
    }

    void onPlayerHandCardButton(int handCardIndex) {
        attempt(onError, () -> {
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
        });
    }

    void onStartGameButton() {
        attempt(onError, () -> {
            ((Game) model).startGame();

            handleChangeCurrentPlayer(model.getStartingPlayerIndex());

            view.renderDrawPile(
                    model.getCanDraw(),
                    model.isDrawPileEmpty()
            );
            view.buildAndRenderTurnControlSection(
                    model.getIsGameOngoing(),
                    model.canPlaySelected(),
                    model.canEndTurn()
            );
        });
    }

}
