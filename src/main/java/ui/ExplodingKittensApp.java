package ui;

import domain.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class ExplodingKittensApp extends Application {

    private final AssetManager assets = new AssetManager();
    private static final String englishLanguage = "English";
    private static final String spanishLanguage = "Spanish";
    private Consumer<String> errorHandler;

	@Override
    public void start(Stage stage) {
		this.assets.loadGlobalFiles(englishLanguage);
        this.errorHandler = message -> {
            showErrorScreen(message, stage);
        };

        showStartScreen(stage);
    }

    public void switchLanguageAndView(Stage stage, String language) {
        assets.loadLanguageAndCardMetadata(language);
        showPlayerCreateScreen(stage);
    }

    private void showStartScreen(Stage stage) {
        StartController controller = new StartController(assets);

        controller.setOnEnglishPlay(() -> switchLanguageAndView(stage, englishLanguage));
        controller.setOnSpanishPlay(() -> switchLanguageAndView(stage, spanishLanguage));

        Scene startScene = controller.getStartScene();
        setScene(startScene, stage);
    }

    private void showPlayerCreateScreen(Stage stage) {
        PlayerCreateController controller = new PlayerCreateController(assets);

        controller.setOnError(errorHandler);
        controller.setOnSuccess(() -> initializeGame(controller, stage));
        controller.setOnRestart(() -> showStartScreen(stage));

        Scene playerCreateScene = controller.getPlayerCreateScene();
        setScene(playerCreateScene, stage);
    }

    private void showErrorScreen(String message, Stage stage) {
        ErrorController controller = new ErrorController(assets, message);

        Scene errorScene = controller.getErrorScene();
        setScene(errorScene, stage);
    }

    private void initializeGame(PlayerCreateController createController, Stage stage) {
        List<String> playerNames = createController.getConfirmedNames();
        Game model = new Game(playerNames);

        showPlayerDeckScreen(model, stage);
    }

    private void showPlayerDeckScreen(Game model, Stage stage) {
        PlayerDeckController controller = new PlayerDeckController(model, assets);

        controller.setOnError(errorHandler);

        Scene playerDeckScene = controller.getPlayerDeckScene();
        setScene(playerDeckScene, stage);
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