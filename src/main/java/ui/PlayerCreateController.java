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

    private int numPlayerFields;
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
        if (isBelowMaxPlayers()) {
            numPlayerFields++;
            view.addPlayerField(numPlayerFields);

            updateAddPlayerButton();
        }
    }

    private boolean isBelowMaxPlayers() {
        return numPlayerFields < GameConstants.MAX_PLAYERS;
    }

    private void updateAddPlayerButton() {
        if (numPlayerFields == GameConstants.MAX_PLAYERS) {
            view.setAddPlayerButtonDisabled(true);
        }
    }

    void onConfirmNames() {
        populateConfirmedNames();

        attempt(onError, () -> onSuccess.run());
    }

    void populateConfirmedNames() {
        for (String input : view.getPlayerNamesFromFields()) {
            addToConfirmedNames(input);
        }
    }

    private void addToConfirmedNames(String name) {
        if (!name.isBlank()) {
            confirmedNames.add(name.trim());
        }
    }

    int getNumPlayerFields() {
        return numPlayerFields;
    }

}