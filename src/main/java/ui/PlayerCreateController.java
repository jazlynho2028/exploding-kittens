package ui;

import domain.Game;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {
    private final PlayerCreateView view;
    private List<String> confirmedNames;
    private Consumer<String> onError;
    private Runnable onSuccess;
    private Runnable onBack;

    public PlayerCreateController(AssetProvider assets) {
        this.view = new PlayerCreateView(assets);
        this.onError = message -> { };


        buildAndBindUI();
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    private void buildAndBindUI() {
        view.addPlayerFieldEntry();
        view.addPlayerFieldEntry();
        bindUI();
    }

    private void bindUI() {
        view.addPlayerButton.setOnMouseClicked(e -> onAddPlayer());
        view.confirmButton.setOnMouseClicked(e -> onConfirmNames());
        view.backButton.setOnMouseClicked(e -> onBack.run());
    }

    private void onAddPlayer() {
        view.addPlayerFieldEntry();
    }

    private void onConfirmNames() {
        List<String> rawNames = view.getEnteredNames();

        List<String> names = new ArrayList<>();
        for (String input : rawNames) {
            if (input != null && !input.isBlank()) {
                names.add(input.trim());
            }
        }

        if (names.size() < 2) {
            onError.accept("You need at least 2 players to start!");
            return;
        }

        this.confirmedNames = names;

        try {
            if (onSuccess != null) {
                onSuccess.run();
            }
        }
        catch (Exception e) {
            onError.accept("Error initializing game: " + e.getMessage());
        }
    }

    public List<String> getConfirmedNames() {
        return new ArrayList<>(confirmedNames);
    }

    public Scene getPlayerCreateScene() {
        return view.createPlayerCreateScene();
    }
}