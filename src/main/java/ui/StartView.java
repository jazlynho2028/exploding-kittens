package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Objects;

public class StartView {
    private final AssetProvider assetProvider;
    private final StackPane root;
    private final Button playButtonEnglish;
    private final Button playButtonSpanish;

    public StartView(AssetProvider assetProvider) {
        this.assetProvider = assetProvider;
        this.root = new StackPane();
        this.playButtonEnglish = new Button();
        this.playButtonSpanish = new Button();

        buildUI();
    }

    public void bindEnglishPlayButton(Runnable handler) {
        playButtonEnglish.setOnMouseClicked(e -> handler.run());
    }

    public void bindSpanishPlayButton(Runnable handler) {
        playButtonSpanish.setOnMouseClicked(e -> handler.run());
    }

    public Scene createStartScene () {
        return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
    }

    private void buildUI() {
        StackPane startScreen = buildStartScreen();
        root.getChildren().add(startScreen);
    }

    private StackPane buildStartScreen() {
        StackPane startScreen = new StackPane();

        ImageView explosionCatImage = buildExplosionImage(assetProvider);
        VBox contentSection = buildContentSection();

        startScreen.getChildren().addAll(explosionCatImage, contentSection);
        return startScreen;

    }

    private VBox buildContentSection() {
        VBox contentSection = new VBox();
        contentSection.getStyleClass().add("start-content-section");
        contentSection.setAlignment(Pos.CENTER);

        Text titleText = buildTitleText(assetProvider);

        HBox playButtonsContainer = buildPlayButtonsContainer();

        contentSection.getChildren().addAll(
                titleText, playButtonsContainer
        );

        return contentSection;
    }

    private HBox buildPlayButtonsContainer() {
        HBox playButtonsContainer = new HBox();
        playButtonsContainer.getStyleClass().add("play-buttons-container");
        playButtonsContainer.setAlignment(Pos.CENTER);

        renderPlayButton(playButtonEnglish, "English");
        renderPlayButton(playButtonSpanish, "Spanish");

        playButtonsContainer.getChildren().addAll(playButtonEnglish, playButtonSpanish);

        return playButtonsContainer;
    }

    static ImageView buildExplosionImage(AssetProvider assetProvider) {
        Image image = assetProvider.getImage("explosion");
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(UIConstants.BACKGROUND_IMAGE_WIDTH);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    static Text buildTitleText(AssetProvider assetProvider) {
        Text titleText = new Text(assetProvider.getString("global.title"));
        titleText.getStyleClass().addAll("h1", "title");

        return titleText;
    }

    private void renderPlayButton(Button playButton, String languageName) {
        playButton.getStyleClass().addAll("play-button", "h2");

        setPlayButtonText(playButton, languageName);
    }

    private void setPlayButtonText(Button playButton, String languageName) {
        if (Objects.equals(languageName, "English")) {
            playButton.setText(assetProvider.getString("startScreen.English"));
        }
        else {
            playButton.setText(assetProvider.getString("startScreen.Spanish"));
        }
    }

}

