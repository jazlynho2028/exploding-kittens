package ui;

import domain.Card;
import domain.CardType;
import domain.Game;
import domain.GameConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.Optional;
import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerDeckController {

    private final PlayerDeckView view;
    private final Game model;

    private Consumer<String> onError;
    private Runnable onRestart;
    Optional<Consumer<Integer>> pendingTargetAction = Optional.empty();

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
        this.onRestart = () -> { };
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
                model.getIsGameOngoing(),
                model.getDeadIndices()
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
            if (model.getCurrentPlayerIndex() != playerIndex) {
                if (pendingTargetAction.isPresent()) {
                    Consumer<Integer> action = pendingTargetAction.get();
                    pendingTargetAction = Optional.empty();

                    action.accept(playerIndex);

                    view.renderPlayerNameTags(model.getCurrentPlayerIndex(),
                            model.getIsGameOngoing(), model.getDeadIndices());
                } else {
                    handleChangeCurrentPlayer(playerIndex);
                    updateTurnControls();
                }
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
                model.getIsGameOngoing(),
                model.getDeadIndices()
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
        boolean isDefusable = model.isDefusable();

        if (isDefusable) {
            view.bindDefuseButton(this::onDefuseButton);
        }
        else {
            view.bindExplodeButton(this::onExplodeButton);
        }

        int drawPileSizeAfterDraw = model.getDrawPileSize() - 1;
        view.buildExplodeOverlay(
                isDefusable, cardId, drawPileSizeAfterDraw);
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

            updateDiscardPile();
            rebindHandCards();
            updateTurnControls();

            if (cardType == CardType.GODCAT) {
                view.bindGodcatConfirmButton(this::onGodcatConfirm);
                view.buildGodcatOverlay(GameConstants.GODCAT_CARDTYPE_OPTIONS);
            }
            else {
                updateByCardType(cardType);
            }
        });
    }

    void updateByCardType(CardType cardType) {
        switch (cardType) {
            case SKIP:
			case SUPER_SKIP:
			case CATOMIC_BOMB:
				renderNextTurn();
                break;
            case SEE_THE_FUTURE:
                view.buildSeeTheFutureOverlay(model.getSeeTheFutureCardIds());
                break;
            case TARGETED_ATTACK:
                pendingTargetAction = Optional.of(model::applyTargetedAttack);
                view.renderPlayerNameTags(model.getCurrentPlayerIndex(), false,
                        model.getDeadIndices());
                view.renderTurnControlSection(false, false);
                break;
            case RAGEBAIT:
                pendingTargetAction = Optional.of(model::applyRagebait);
                view.renderPlayerNameTags(model.getCurrentPlayerIndex(), false,
                        model.getDeadIndices());
                view.renderTurnControlSection(false, false);
                break;
			default:
                break;
        }
    }

    private void updateDiscardPile() {
        view.renderDiscardPile(model.canDrawFromDiscard(), model.getTopDiscardId());
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
            updateDiscardPile();
            rebindHandCards();

            renderNextTurn();
        });
    }

    void onExplodeButton() {
        attempt(onError, () -> {
            model.playExplode();

            view.hideOverlay();
            updateDrawPile();

            renderNextTurn();

            if (!model.getIsGameOngoing()) {
                view.buildWinOverlay(model.getWinnerName());
                view.bindPlayAgainButton(onRestart);
            }
        });
    }

    void onGodcatConfirm() {
        attempt(onError, () -> {
            CardType cardType = view.getSelectedGodcatCardType();

            model.applyGodcat(cardType);
            view.hideOverlay();

            updateTurnControls();

            updateByCardType(cardType);
        });
    }

    public void setOnRestart(Runnable handler) {
        onRestart = handler;
    }

}