package ui;

import domain.GameConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static ui.ErrorHandler.attempt;

public class PlayerCreateController {
    private final PlayerCreateView view;
    private final List<String> confirmedNames;

    private int playerFieldsCount;
    private Consumer<String> onError;
    private Runnable onSuccess;
    private Runnable onRestart;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View is injected by for compromise between MVC pattern and " +
                    "testability, defensive copy is not applicable for JavaFX components"
    )
    public PlayerCreateController(PlayerCreateView view) {
        this.view = view;
        this.confirmedNames = new ArrayList<>();
        this.onError = message -> { };
    }

    public Scene buildPlayerCreateScene() {
        buildDependentUI();
        bindUI();

        return view.createPlayerCreateScene();
    }

    void buildDependentUI() {
        for (int i = 0; i < GameConstants.MIN_PLAYERS; i++) {
            onAddPlayer();
        }
    }

    void bindUI() {
        view.bindAddPlayerButton(this::onAddPlayer);
        view.bindConfirmButton(this::onConfirmNames);
        view.bindRestartButton(onRestart);
    }

    public void setOnError(Consumer<String> handler) {
        onError = handler;
    }

    public void setOnSuccess(Runnable handler) {
        onSuccess = handler;
    }

    public void setOnRestart(Runnable handler) {
        onRestart = handler;
    }

    public List<String> getConfirmedNames() {
        return List.copyOf(confirmedNames);
    }

    void onAddPlayer() {
        boolean isBelowMaxPlayers = playerFieldsCount < GameConstants.MAX_PLAYERS;

        if (isBelowMaxPlayers) {
            playerFieldsCount++;
            view.addPlayerField(playerFieldsCount);

            boolean isAtMaxPlayers = playerFieldsCount == GameConstants.MAX_PLAYERS;

            if (isAtMaxPlayers) {
                view.setAddPlayerButtonDisabled(true);
            }
        }
    }

    void onConfirmNames() {
        populateConfirmedNames();

        attempt(onError, () -> onSuccess.run());
    }

    void populateConfirmedNames() {
        List<String> inputsFromView = view.getPlayerNamesFromFields();

        for (String input : inputsFromView) {
            if (!input.isBlank()) {
                confirmedNames.add(input.trim());
            }
        }
    }

    int getPlayerFieldsCount() {
        return playerFieldsCount;
    }

}