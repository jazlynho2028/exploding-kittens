package ui;

import domain.Game;
import domain.GameData;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.function.Consumer;

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

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void buildAndBindDependentUI() {
        buildDependentUI();
        bindUI();
    }

    private void buildDependentUI() {
        try {
            view.buildAndAddPlayerHandCards(
                    this.model.getCurrentPlayerHandIds(),
                    this.model.getIsFaceUp(),
                    this.model.getCanDraw()
            );
            view.buildAddRenderPlayerNameTags(
                    this.model.getPlayerNames(),
                    this.model.getCurrentPlayerIndex(),
                    this.model.getIsGameOngoing()
            );
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
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
            ((Game) model).changeCurrentPlayerIndex(playerIndex);
            ((Game) model).setFaceUpToFalse();

            view.renderPlayerNameTags(
                    model.getCurrentPlayerIndex(),
                    model.getIsGameOngoing()
            );
            view.renderHandVisibilityButton(model.getIsFaceUp());
            buildAddBindPlayerHandCards();
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void buildAddBindPlayerHandCards() {
        try {
            view.buildAndAddPlayerHandCards(
                    model.getCurrentPlayerHandIds(),
                    model.getIsFaceUp(),
                    model.getCanDraw()
            );
            view.bindPlayerHandCardButtons(this::onPlayerHandCardButton);
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void onDrawPile() {
        try {
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
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void onHandVisibilityButton() {
        try {
            ((Game) model).setIsFaceUpToOpposite();

            view.renderHandVisibilityButton(model.getIsFaceUp());
            buildAddBindPlayerHandCards();
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void onPlayerHandCardButton(int handCardIndex) {
        try {
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
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void onStartGameButton() {
        try {
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
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

}
