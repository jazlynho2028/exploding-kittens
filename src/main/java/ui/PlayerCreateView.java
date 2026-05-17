package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.List;

import static ui.StartView.buildExplosionImage;
import static ui.StartView.buildTitleText;

public class PlayerCreateView {

    private final AssetProvider assetProvider;
    private final StackPane root;

    public final VBox playerFieldsContainer;
    public final Button addPlayerButton;
    public final Button confirmButton;
    public final Button backButton;

    private final List<TextField> textFields = new ArrayList<>();

    public PlayerCreateView(AssetProvider assets) {
        this.assetProvider = assets;
        this.root = new StackPane();

        this.playerFieldsContainer = new VBox();
        this.addPlayerButton = new Button("+");
        this.confirmButton = new Button("CONFIRM");
        this.backButton = new Button();

        buildUI();
    }

    public Scene createPlayerCreateScene() {
        return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
    }

    private void buildUI() {
        root.getStyleClass().add("root");

        ImageView backgroundImage = buildBackgroundImage();

        StackPane createScreen = buildCreateScreen();
        StackPane overlayLayer = buildOverlayLayer();

        root.getChildren().addAll(backgroundImage, createScreen, overlayLayer);
    }

    private StackPane buildCreateScreen() {
        StackPane createScreen = new StackPane();

        VBox contentSection = buildContentSection();
        createScreen.getChildren().add(contentSection);

        return createScreen;
    }

    private VBox buildContentSection() {
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.getStyleClass().add("content-section");

        setupPlayerFieldsContainer();
        Button addPlayerButton = buildAddPlayerButton();
        Button confirmButton = buildConfirmButton();

        content.getChildren().addAll(
                buildTitleText(),
                playerFieldsContainer,
                addPlayerButton,
                confirmButton);

        return content;
    }

    private void setupPlayerFieldsContainer() {
        playerFieldsContainer.setAlignment(Pos.CENTER);
        playerFieldsContainer.getStyleClass().add("player-fields-container");
    }

    private Button buildAddPlayerButton() {
        addPlayerButton.getStyleClass().addAll("add-player-button");
        return addPlayerButton;
    }

    private Button buildConfirmButton() {
        confirmButton.getStyleClass().addAll("play-button", "h2");
        return confirmButton;
    }

    private StackPane buildOverlayLayer() {
        StackPane overlayLayer = new StackPane();
        overlayLayer.setPickOnBounds(false);

        SVGPath backIcon = new SVGPath();
        backIcon.setContent(assetProvider.getSvg("restart"));
        backIcon.getStyleClass().add("restart-icon");

        backButton.getStyleClass().add("icon-button");
        backButton.setGraphic(backIcon);

        overlayLayer.getChildren().add(backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(UIConstants.BUTTON_MARGIN_INSETS));

        return overlayLayer;
    }

    private ImageView buildBackgroundImage() {
        ImageView backgroundImage = buildExplosionImage(assetProvider);
        backgroundImage.setOpacity(UIConstants.BACKGROUND_IMAGE_OPACITY);

        return backgroundImage;
    }

    public TextField createPlayerTextField(int index) {
        TextField field = new TextField();
        field.setPromptText("PLAYER " + index);
        field.getStyleClass().addAll("name-enter", "h5");
        field.setAlignment(Pos.CENTER_LEFT);
        return field;
    }

    public void updatePlayerFieldsDisplay(int numberOfPlayers) {
        playerFieldsContainer.getChildren().clear();
        textFields.clear();

        for (int i = 1; i <= numberOfPlayers; i++) {
            TextField field = createPlayerTextField(i);
            textFields.add(field);
            playerFieldsContainer.getChildren().add(field);
        }
    }

    public List<String> getPlayerNamesFromFields() {
        List<String> names = new ArrayList<>();
        for (TextField field : textFields) {
            names.add(field.getText());
        }
        return names;
    }

    public void addPlayerField(int index) {
        TextField field = createPlayerTextField(index);

        textFields.add(field);
        playerFieldsContainer.getChildren().add(field);
    }

    public void setAddPlayerButtonDisabled(boolean disabled) {
        addPlayerButton.setDisable(disabled);
    }
}