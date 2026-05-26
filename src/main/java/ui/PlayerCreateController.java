package ui;

import domain.GameConstants;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {
    private final PlayerCreateView view;
    private final List<String> playerFields = new ArrayList<>();

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
        this.onError = message -> { };
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

    public void buildAndBindDependentUI() {
        for (int i = 0; i < GameConstants.MIN_PLAYERS; i++) {
            playerFields.add("");
        }
        view.bindUI(
                this::onAddPlayer,
                this::onConfirmNames,
                this::onRestartButton
        );
    }

    void onAddPlayer() {
        int visualIndex = playerFields.size() + 1;

        playerFields.add("");

        view.addPlayerField(visualIndex);

        view.setAddPlayerButtonDisabled(
                playerFields.size() >= GameConstants.MAX_PLAYERS
        );
    }

    void onConfirmNames() {
        List<String> names = new ArrayList<>();

        List<String> inputsFromView = view.getPlayerNamesFromFields();

        for (String input : inputsFromView) {
            if (!input.isBlank()) {
                names.add(input.trim());
            }
        }

        this.confirmedNames = names;

        try {
            onSuccess.run();
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    void onRestartButton() {
        onRestart.run();
    }

    public List<String> getConfirmedNames() {
        return new ArrayList<>(confirmedNames);
    }

    public int getPlayerNumbers() {
        return playerFields.size();
    }

}