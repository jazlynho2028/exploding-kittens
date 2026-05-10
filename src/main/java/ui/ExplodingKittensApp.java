package ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class ExplodingKittensApp extends Application {

    @Override
    public void start(Stage stage) {
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showPlayerDeckView();
    }

    public static void main(String[] args) {
        launch();
    }
}
