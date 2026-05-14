package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();
    private final String languageName = "English";

    @Override
    public void start(Stage stage) {
        assets.loadGlobalFiles(languageName);

        StartController controller = new StartController(assets);

        Scene startScene = controller.getStartScene();
        setScene(startScene, stage);
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