package ui;

import domain.GameConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {
    private final PlayerCreateView view;

    private int playerFieldsCount;
    private List<String> confirmedNames;
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
        this.playerFieldsCount = 0;
        this.confirmedNames = new ArrayList<>();
        this.onError = message -> { };
    }

    public Scene buildPlayerCreateScene() {
        buildDependentUI();
        bindUI();

        return view.createPlayerCreateScene();
    }

    private void buildDependentUI() {
        for (int i = 0; i < GameConstants.MIN_PLAYERS; i++) {
            onAddPlayer();
        }
    }

    private void bindUI() {
        view.bindAddPlayerButton(this::onAddPlayer);
        view.bindConfirmButton(this::onConfirmNames);
        view.bindRestartButton(this::onRestartButton);
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnRestart(Runnable onRestart) {
        this.onRestart = onRestart;
    }

    void onAddPlayer() {
        playerFieldsCount++;
        view.addPlayerField(playerFieldsCount);

        view.setAddPlayerButtonDisabled(
                playerFieldsCount >= GameConstants.MAX_PLAYERS
        );
    }

    void onConfirmNames() {
        populateConfirmedNames();

        try {
            onSuccess.run();
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void populateConfirmedNames() {
        List<String> inputsFromView = view.getPlayerNamesFromFields();

        for (String input : inputsFromView) {
            if (!input.isBlank()) {
                confirmedNames.add(input.trim());
            }
        }
    }

    void onRestartButton() {
        onRestart.run();
    }

    public List<String> getConfirmedNames() {
        return List.copyOf(confirmedNames);
    }

    int getPlayerFieldsCount() {
        return playerFieldsCount;
    }

}