package ui;

import javafx.scene.Scene;

import java.util.function.Consumer;

public class StartController {

    private final StartView view;
    private Consumer<String> onError;

    public StartController(AssetProvider assetProvider) {
        this.view = new StartView(assetProvider);
        this.onError = message -> {};
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}