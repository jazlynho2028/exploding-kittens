package ui;

import javafx.scene.Scene;

import java.util.function.Consumer;

public class StartController {

    private final StartView view;

    public StartController(AssetProvider assetProvider) {
        this.view = new StartView(assetProvider);
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}