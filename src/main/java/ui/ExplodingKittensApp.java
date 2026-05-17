package ui;

import domain.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();
    private static final String languageName = "English";

    @Override
    public void start(Stage stage) {
        assets.loadGlobalFiles(languageName);

        showStartScreen(stage);
    }

    private void showStartScreen(Stage stage) {
        StartController controller = new StartController(assets);

        controller.setOnPlay(() -> showPlayerCreateScreen(stage));

        setScene(controller.getStartScene(), stage);
    }

    private void showPlayerCreateScreen(Stage stage) {
        PlayerCreateController controller = new PlayerCreateController(assets);

        controller.setOnError(message -> showErrorScreen(message, stage));
        controller.setOnSuccess(() -> showPlayerDeckScreen(controller, stage));
        controller.setOnBack(() -> showStartScreen(stage));
        setScene(controller.getPlayerCreateScene(), stage);
    }

    private void showPlayerDeckScreen(PlayerCreateController createController, Stage stage) {
        List<String> playerNames = createController.getConfirmedNames();
        Game model = new Game(playerNames);

    }

    private void showErrorScreen(String message, Stage stage) {

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