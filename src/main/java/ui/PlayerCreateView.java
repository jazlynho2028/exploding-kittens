package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class PlayerCreateView {

    private final AssetProvider assets;
    private final StackPane root;

    public final VBox playerFieldsContainer;
    public final Button addPlayerButton;
    public final Button confirmButton;
    public final Button backButton;

    private final List<TextField> playerFields = new ArrayList<>();
    private static final int MAX_PLAYERS = 4;

    public PlayerCreateView(AssetProvider assets) {
        this.assets = assets;
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

    private Text buildTitleText() {
        Text title = new Text("EXPLODING KITTENS");
        title.getStyleClass().addAll("title", "h1");
        return title;
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
        confirmButton.getStyleClass().addAll("start-game-button", "h3");
        return confirmButton;
    }

    private ImageView buildBackgroundImage() {
        Image image = assets.getImage("placeholder");
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setOpacity(UIConstants.BACKGROUND_IMAGE_OPACITY);

        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty());
        return imageView;
    }

    private StackPane buildOverlayLayer() {
        StackPane overlayLayer = new StackPane();
        overlayLayer.setPickOnBounds(false);

        SVGPath backIcon = new SVGPath();
        backIcon.setContent(assets.getSvg("restart"));
        backIcon.getStyleClass().add("restart-icon");

        backButton.getStyleClass().add("icon-button");
        backButton.setGraphic(backIcon);

        overlayLayer.getChildren().add(backButton);
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(UIConstants.BUTTON_MARGIN_INSETS));

        return overlayLayer;
    }

    public void addPlayerFieldEntry() {
        if (playerFields.size() >= MAX_PLAYERS) {
            return;
        }

        TextField field = new TextField();
        field.setPromptText("PLAYER " + (playerFields.size() + 1));
        field.getStyleClass().addAll("name-enter", "h5");
        field.setAlignment(Pos.CENTER_LEFT);

        playerFields.add(field);
        renderPlayerFields();
    }

    public void renderPlayerFields() {
        playerFieldsContainer.getChildren().clear();
        playerFieldsContainer.getChildren().addAll(playerFields);

        boolean isFull = playerFields.size() >= MAX_PLAYERS;
        addPlayerButton.setDisable(isFull);
    }

    public List<String> getEnteredNames() {
        List<String> names = new ArrayList<>();
        for (TextField field : playerFields) {
            if (!field.getText().isBlank()) {
                names.add(field.getText().trim());
            }
        }
        return names;
    }
}