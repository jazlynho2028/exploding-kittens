package ui;

import domain.Game;
import javafx.scene.Scene;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {

    private final PlayerCreateView view;
    private final Game model;
    private Consumer<String> onError;
    private Runnable onSuccess;
    private Runnable onBack;

    public PlayerCreateController(Game model, AssetProvider assets) {
        this.model = model;
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
        List<String> names = view.getEnteredNames();

        if (names.size() < 2) {
            onError.accept("You need at least 2 players to start!");
            return;
        }

        try {
            //send the players over to the game class
            if (onSuccess != null) {
                onSuccess.run();
            }
        }
        catch (Exception e) {
            onError.accept("Error initializing game: " + e.getMessage());
        }
    }

    public Scene getPlayerCreateScene() {
        return view.createPlayerCreateScene();
    }
}