package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {
    private final PlayerCreateView view;
    private final AssetProvider assets;
    private final List<String> playerFields = new ArrayList<>();

    private List<String> confirmedNames;
    private Consumer<String> onError;
    private Runnable onSuccess;
    private Runnable onRestart;

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View is injected by for compromise between MVC pattern and " +
                    "testability, defensive copy is not applicable for JavaFX components"
    )
    public PlayerCreateController(AssetProvider assets, PlayerCreateView view) {
        this.view = view;
        this.assets = assets;
        this.onError = message -> { };

        buildAndBindUI();
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

    void buildAndBindUI() {
        for (int i = 0; i < MIN_PLAYERS; i++) {
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

        if (visualIndex > MAX_PLAYERS) {
            onError.accept(assets.getString("error.maxPlayers"));
            return;
        }

        playerFields.add("");

        view.addPlayerField(visualIndex);

        view.setAddPlayerButtonDisabled(
                playerFields.size() >= MAX_PLAYERS
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

        if (names.size() < MIN_PLAYERS) {
            onError.accept(assets.getString("error.minPlayers"));
            return;
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