package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ErrorView {

	private final AssetProvider assetProvider;
	private final StackPane root;
	private final String message;

	private final Button restartButton;

	public ErrorView(AssetProvider assetProvider, String message) {
		root = new StackPane();

		this.assetProvider = assetProvider;
		this.message = message;
		this.restartButton = new Button();

		buildUI();
	}

	public void bindRestartButton(Runnable handler) {
		restartButton.setOnMouseClicked(e -> handler.run());
	}

	public Scene createErrorScene() {
		return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
	}

	private void buildUI() {
		StackPane errorScreen = buildErrorScreen();
		StackPane overlayLayer = PlayerCreateView.buildOverlayLayer(
				assetProvider, restartButton
		);

		root.getChildren().addAll(errorScreen, overlayLayer);
	}

	private StackPane buildErrorScreen() {
		StackPane errorScreen = new StackPane();

		VBox errorTextContainer = buildErrorTextContainer();

		errorScreen.getChildren().add(errorTextContainer);

		return errorScreen;
	}

	private VBox buildErrorTextContainer() {
		VBox errorTextContainer = new VBox();
		errorTextContainer.setAlignment(Pos.CENTER);

		Text errorTitleText = buildText(
				assetProvider.getString("error.title"),
				"h1", "title");

		Text errorCaptionText = buildText(
				message, "h2", "subtitle");

		errorTextContainer.getChildren().addAll(errorTitleText, errorCaptionText);

		return errorTextContainer;
	}

	private Text buildText(String message, String... styleClasses) {
		Text text = new Text(message);
		text.getStyleClass().addAll(styleClasses);

		return text;
	}

}