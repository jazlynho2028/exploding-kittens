package ui;

import javafx.scene.Scene;
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

    public PlayerCreateController(AssetProvider assets) {
        this.view = new PlayerCreateView(assets);
        this.assets = assets;
        this.onError = message -> { };

        buildAndBindUI();
    }

    PlayerCreateController(AssetProvider assets, PlayerCreateView view) {
        this.view = view;
        this.assets = assets;
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

    private void buildAndBindUI() {
        onAddPlayer();
        onAddPlayer();
        bindUI();
    }

    private void bindUI() {
        view.addPlayerButton.setOnMouseClicked(e -> onAddPlayer());
        view.confirmButton.setOnMouseClicked(e -> onConfirmNames());
        view.restartButton.setOnMouseClicked(e -> onRestart.run());
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
            if (input != null && !input.isBlank()) {
                names.add(input.trim());
            }
        }

        if (names.size() < MIN_PLAYERS) {
            onError.accept(assets.getString("error.minPlayers"));
            return;
        }

        this.confirmedNames = names;

        try {
            if (onSuccess != null) {
                onSuccess.run();
            }
        }
        catch (Exception e) {
            onError.accept(e.getMessage());
        }
    }

    public List<String> getConfirmedNames() {
        return new ArrayList<>(confirmedNames);
    }

    public int getPlayerNumbers() {
        return playerFields.size();
    }

    public Scene getPlayerCreateScene() {
        return view.createPlayerCreateScene();
    }
}