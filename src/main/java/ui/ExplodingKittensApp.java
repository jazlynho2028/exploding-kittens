package ui;

import domain.GameState;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();

    @Override
    public void start(Stage stage) {
        GameState model = new GameState();
        assets.loadGlobalFiles();

        PlayerDeckController controller = new PlayerDeckController(model, assets);

        Scene playerDeckScene = controller.getPlayerDeckScene();
        setScene(playerDeckScene, stage);
    }

    private void setScene(Scene scene, Stage stage) {
        scene.getStylesheets().add(assets.getStylesheet());

        stage.setScene(scene);
        stage.setTitle(UIConstants.TITLE);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
