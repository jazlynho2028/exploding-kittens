package ui;

import domain.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();
    private static final String languageName = "English";

    @Override
    public void start(Stage stage) {
        Game model = new Game();
        assets.loadGlobalFiles(languageName);

        showPlayerDeckScreen(model, stage);
    }

    private void showStartScreen(Stage stage) {
        StartController controller = new StartController(assets);

        Scene startScene = controller.getStartScene();
        setScene(startScene, stage);
    }

    private void showPlayerDeckScreen(Game model, Stage stage) {
        PlayerDeckController controller = new PlayerDeckController(model, assets);

        controller.setOnError(message -> showErrorScreen(message, stage));

        Scene playerDeckScene = controller.getPlayerDeckScene();
        setScene(playerDeckScene, stage);
    }

    private void showErrorScreen(String message, Stage stage) {
        ErrorController controller = new ErrorController(assets, message);

        Scene errorScene = controller.getErrorScene();
        setScene(errorScene, stage);
    }

    private void setScene(Scene scene, Stage stage) {
        scene.getStylesheets().add(assets.getStylesheet());

        stage.setScene(scene);
        stage.setTitle(assets.getString("global.title"));
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
