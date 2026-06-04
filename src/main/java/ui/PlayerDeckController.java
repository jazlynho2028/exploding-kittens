package ui;

import domain.CardType;
import domain.Game;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.List;
import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final Game model;

    private Consumer<String> onError;

    private static final List<CardType> GODCAT_CARD_OPTIONS = List.of(
            CardType.ATTACK,
            CardType.SHUFFLE,
            CardType.SKIP,
            CardType.SEE_THE_FUTURE,
            CardType.CATOMIC_BOMB,
            CardType.SUPER_SKIP,
            CardType.CLONE,
            CardType.SWAP_TOP_AND_BOTTOM,
            CardType.DRAW_FROM_THE_BOTTOM,
            CardType.TARGETED_ATTACK,
            CardType.WINNER_WINNER_CATNIP_DINNER,
            CardType.RAGEBAIT,
            CardType.RECYCLE,
            CardType.DOUBLE_UP,
            CardType.MILD_DRAW
    );

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View and model are injected by for compromise between MVC " +
                    "pattern and testability, defensive copies are not applicable or not " +
                    "desired for JavaFX components and Game objects."
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
        view.bindPlayCardsButton(this::onPlayCardsButton);
        view.bindEndTurnButton(this::onEndTurnButton);
        view.bindNameTags(this::onNameTag);
        view.bindGodcatConfirmButton(this::onGodcatConfirm);
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
            CardType cardType = model.drawFromPile();
            // TODO use ^ return value for UI changes if a card effect needs it

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

            handleNewTurn(model.getStartingPlayerIndex());
        });
    }

    private void handleNewTurn(int newPlayerIndex) {
        handleChangeCurrentPlayer(newPlayerIndex);

        updateDrawPile();
        rebuildTurnControl();
    }

    private void rebuildTurnControl() {
        view.buildAndRenderTurnControlSection(
                model.getIsGameOngoing(),
                model.canPlaySelected(),
                model.canEndTurn()
        );
    }

    void onPlayCardsButton() {
        attempt(onError, () -> {
            CardType cardType = model.playSelectedCards();
            // TODO use ^ return value for UI changes if a card effect needs it

            view.renderDiscardPile(model.canDrawFromDiscard(), model.getTopDiscardId());
            rebindHandCards();
            updateTurnControls();

            if (cardType == CardType.GODCAT) {
                view.showCardSelectOverlay(GODCAT_CARD_OPTIONS);
            }
        });
    }

    void onEndTurnButton() {
        attempt(onError, () -> {
            model.advanceTurn();

            handleNewTurn(model.getCurrentPlayerIndex());
        });
    }

    void onGodcatConfirm() {
        attempt(onError, () -> {
            CardType selectedCardType = view.getSelectedGodcatCardType();
            onConfirmGodcatCard(selectedCardType);
        });
    }

    void onConfirmGodcatCard(CardType cardType) {
        attempt(onError, () -> {
            model.applyCardType(cardType);
            view.hideGodcatOverlay();
        });
    }

}