package ui;

import domain.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();

    @Override
    public void start(Stage stage) {
        Game model = new Game();
        assets.loadGlobalFiles();

        showStartScreen(model, stage);
    }

    private void showStartScreen(Game model, Stage stage) {
        StartController controller = new StartController(assets);

        controller.setOnPlay(() -> showPlayerCreateScreen(model, stage));

        setScene(controller.getStartScene(), stage);
    }

    private void showPlayerCreateScreen(Game model, Stage stage) {
        PlayerCreateController controller = new PlayerCreateController(model, assets);

        controller.setOnError(message -> showErrorScreen(message, stage));
        controller.setOnSuccess(() -> showPlayerDeckScreen(model, stage));
        controller.setOnBack(() -> showStartScreen(model, stage));

        setScene(controller.getPlayerCreateScene(), stage);
    }

    private void showPlayerDeckScreen(Game model, Stage stage) {

    }

    private void showErrorScreen(String message, Stage stage) {

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