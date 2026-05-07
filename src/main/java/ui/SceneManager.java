package ui;

import datasource.*;
import domain.GameState;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;
    private final GameState model;
    private final AssetManager assets;

    public SceneManager(Stage stage, GameState model, AssetManager assets) {
        this.stage = stage;
        this.model = model;
        this.assets = assets;
    }

    public void showPlayerDeckView() {
        assets.loadGlobalFiles();
        PlayerDeckController controller = new PlayerDeckController(
                model,
                this,
                assets
        );
        setScene(controller.getViewRoot());
    }

    public void showErrorView() {
        assets.loadGlobalFiles();
        ErrorController controller = new ErrorController();
        setScene(controller.getViewRoot());
    }

    private void setScene(Parent root) {
        Scene scene = new Scene(
                root,
                UIConstants.SCENE_WIDTH,
                UIConstants.SCENE_HEIGHT
        );

        String cssUrl = assets.getStylesheet();
        scene.getStylesheets().add(cssUrl);

        stage.setScene(scene);
        stage.setTitle(UIConstants.TITLE);
        stage.setResizable(false);

        stage.show();
    }

}
