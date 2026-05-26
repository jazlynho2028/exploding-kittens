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
        this.errorHandler = key -> {
            String errorMsg = assets.getString(key);
            showErrorScreen(errorMsg, stage);
        };

        showStartScreen(stage);
    }

    public void switchLanguageAndView(Stage stage, String language) {
        assets.loadLanguageAndCardMetadata(language);
        showPlayerCreateScreen(stage);
    }

    private void showStartScreen(Stage stage) {
        StartView view = new StartView(assets);
        StartController controller = new StartController(view);

        controller.setOnEnglishPlay(() -> switchLanguageAndView(stage, englishLanguage));
        controller.setOnSpanishPlay(() -> switchLanguageAndView(stage, spanishLanguage));

        Scene startScene = view.createStartScene();
        setScene(startScene, stage);
    }

    private void showPlayerCreateScreen(Stage stage) {
        PlayerCreateView view = new PlayerCreateView(assets);
        PlayerCreateController controller = new PlayerCreateController(view);

        controller.setOnError(errorHandler);
        controller.setOnSuccess(() -> initializeGame(controller, stage));
        controller.setOnRestart(() -> showStartScreen(stage));

        Scene playerCreateScene = view.createPlayerCreateScene();
        setScene(playerCreateScene, stage);
    }

    private void showErrorScreen(String message, Stage stage) {
        ErrorView view = new ErrorView(assets, message);
        ErrorController controller = new ErrorController(view);

        controller.setOnRestart(() -> showStartScreen(stage));

        Scene errorScene = view.createErrorScene();
        setScene(errorScene, stage);
    }

    private void initializeGame(PlayerCreateController createController, Stage stage) {
        List<String> playerNames = createController.getConfirmedNames();
        Game model = new Game(playerNames);

        showPlayerDeckScreen(model, stage);
    }

    private void showPlayerDeckScreen(Game model, Stage stage) {
        PlayerDeckView view = new PlayerDeckView(assets);
        PlayerDeckController controller = new PlayerDeckController(model, view);

        controller.setOnError(errorHandler);
        controller.buildAndBindUI();

        Scene playerDeckScene = view.createPlayerDeckScene();
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