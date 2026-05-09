package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;

    public StartController(AssetManager assets) {
        this.view = new StartView(assets);
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}