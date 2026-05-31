package ui;

import domain.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

        Scene startScene = controller.buildStartScene();
        setScene(startScene, stage);
    }

    private void showPlayerCreateScreen(Stage stage) {
        PlayerCreateView view = new PlayerCreateView(assets);
        PlayerCreateController controller = new PlayerCreateController(view);

        controller.setOnError(errorHandler);
        controller.setOnSuccess(() -> initializeGame(controller, stage));
        controller.setOnRestart(() -> showStartScreen(stage));

        Scene playerCreateScene = controller.buildPlayerCreateScene();
        setScene(playerCreateScene, stage);
    }

    private void showErrorScreen(String message, Stage stage) {
        ErrorView view = new ErrorView(assets, message);
        ErrorController controller = new ErrorController(view);

        controller.setOnRestart(() -> showStartScreen(stage));

        Scene errorScene = controller.buildErrorScene();
        setScene(errorScene, stage);
    }

    private void initializeGame(PlayerCreateController createController, Stage stage) {
        List<Player> players = createPlayers(createController.getConfirmedNames());
        Deck drawPile = DeckBuilder.buildDeckWithoutExplodeAndAddDefuse(players.size());
        Deck discardPile = new Deck(new ArrayDeque<>(), new Random());
        TurnManager turnManager = new TurnManager(players);

        Game model = new Game(players, drawPile, discardPile, turnManager);
        model.setUp();

        showPlayerDeckScreen(model, stage);
    }

    private List<Player> createPlayers(List<String> names) {
        List<Player> players = new ArrayList<>();

        for (String name : names) {
            players.add(new Player(name));
        }

        return players;
    }

    private void showPlayerDeckScreen(Game model, Stage stage) {
        PlayerDeckView view = new PlayerDeckView(assets);
        PlayerDeckController controller = new PlayerDeckController(model, view);

        controller.setOnError(errorHandler);

        Scene playerDeckScene = controller.buildPlayerDeckScene();
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