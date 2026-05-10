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
        this.onError = message -> {};

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

    }

    private void bindUI() {

    }

    private void onAddPlayer() {
        view.addPlayerFieldEntry();
    }

    private void onConfirmNames() {

    }

    public Scene getPlayerCreateScene() {
        return view.createPlayerCreateScene();
    }
}