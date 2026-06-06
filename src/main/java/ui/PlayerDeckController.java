package ui;

import domain.Card;
import domain.CardType;
import domain.Game;
import domain.GameConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final Game model;
    CardType pendingTargetCard = null;

    private Consumer<String> onError;

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
            if (pendingTargetCard != null) {
                applyPendingTargetCard(playerIndex);

                pendingTargetCard = null;

                view.disableTargetSelectionMode(model.getCurrentPlayerIndex(), model.getIsGameOngoing());

                handleChangeCurrentPlayer(model.getCurrentPlayerIndex());
                updateTurnControls();
            }
            else if (model.getCurrentPlayerIndex() != playerIndex) {
                handleChangeCurrentPlayer(playerIndex);
            }
        });
    }

    void applyPendingTargetCard(int playerIndex) {

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
            Card drawnCard = model.drawFromPile();

            if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
                handleDrawExplodingKitten(drawnCard.getId());
            }
            else {
                rebindHandCards();
                updateDrawPile();
                updateTurnControls();
            }
        });
    }

    private void handleDrawExplodingKitten(String cardId) {
        boolean hasDefuse = model.currentPlayerHasDefuse();

        if (hasDefuse) {
            view.bindDefuseButton(this::onDefuseButton);
        }
        else {
            view.bindExplodeButton(this::onExplodeButton);
        }

        int drawPileSizeAfterDraw = model.getDrawPileSize() - 1;
        view.buildExplodeOverlay(
                hasDefuse, cardId, drawPileSizeAfterDraw);
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

    void onPlayCardsButton() {
        attempt(onError, () -> {
            CardType cardType = model.playSelectedCards();

            view.renderDiscardPile(model.canDrawFromDiscard(), model.getTopDiscardId());
            rebindHandCards();
            updateTurnControls();

            switch (cardType) {
                case SKIP:
                    handleChangeCurrentPlayer(model.getCurrentPlayerIndex());
                    break;
                case GODCAT:
                    view.bindGodcatConfirmButton(this::onGodcatConfirm);
                    view.buildGodcatOverlay(GameConstants.GODCAT_CARDTYPE_OPTIONS);
                    break;
                case TARGETED_ATTACK:
                    pendingTargetCard = cardType;
                    view.enableTargetSelectionMode(model.getCurrentPlayerIndex());
                    view.renderTurnControlSection(false, false);
                    break;
                default:
                    break;
            }
        });
    }

    void onEndTurnButton() {
        attempt(onError, () -> {
            model.advanceTurn();

            renderNextTurn();
        });
    }

    private void renderNextTurn() {
        int newPlayerIndex = model.getCurrentPlayerIndex();
        handleChangeCurrentPlayer(newPlayerIndex);
        updateDrawPile();
        updateTurnControls();
    }

    void onDefuseButton() {
        attempt(onError, () -> {
            model.playDefuse(view.getExplodingKittenInsertIndex());

            view.hideOverlay();
            rebindHandCards();

            renderNextTurn();
        });
    }

    void onExplodeButton() {
        attempt(onError, () -> {
            model.playExplode();

            view.hideOverlay();

            renderNextTurn();
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
            model.applyGodcat(cardType);
            view.hideOverlay();
        });
    }

}