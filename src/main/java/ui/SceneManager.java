package ui;

import datasource.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;
    private final AssetManager assets;

    public SceneManager(Stage stage, AssetManager assets) {
        this.stage = stage;
        this.assets = assets;
    }

    public void showStartView() {
        assets.loadGlobalFiles();

        StartController controller = new StartController(assets);

        Scene startScene = controller.getStartScene();
        setScene(startScene);
    }

    private void setScene(Scene scene) {
        stage.setScene(scene);
        stage.setTitle(UIConstants.TITLE);
        stage.setResizable(false);

        stage.show();
    }

}