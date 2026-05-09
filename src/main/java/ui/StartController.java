package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;

    public StartController(AssetProvider assetProvider) {
        this.view = new StartView(assetProvider);
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}