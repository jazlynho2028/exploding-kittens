package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class StartView {
    private final AssetProvider assetProvider;
    private final StackPane root;
    public final Button playButton;

    public StartView(AssetProvider assetProvider) {
        this.assetProvider = assetProvider;
        this.root = new StackPane();
        this.playButton = new Button();

        buildUI();
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

        Text titleText = buildTitleText();
        Button button = buildPlayButton();

        contentSection.getChildren().addAll(titleText, button);

        return contentSection;
    }

    static ImageView buildExplosionImage(AssetProvider assetProvider) {
        Image image = assetProvider.getImage("placeholder");
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(UIConstants.BACKGROUND_IMAGE_WIDTH);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    private Text buildTitleText() {
        Text titleText = new Text(assetProvider.getString("global.title"));
        titleText.getStyleClass().addAll("h1", "title");

        return titleText;
    }

    private Button buildPlayButton() {
        playButton.getStyleClass().addAll("play-button", "h2");

        playButton.setText(assetProvider.getString("startScreen.play"));

        return playButton;
    }
}