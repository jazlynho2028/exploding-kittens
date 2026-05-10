package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ErrorView {

	private final StackPane root;
	private final String message;


	public ErrorView(String message) {
		root = new StackPane();
		this.message = message;

		buildUI();
	}

	public Scene createErrorScene() {
		return new Scene(root, UIConstants.SCENE_WIDTH, UIConstants.SCENE_HEIGHT);
	}

	private void buildUI() {
		StackPane errorScreen = buildErrorScreen();
		root.getChildren().add(errorScreen);
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
				UIConstants.ERROR_MESSAGE_TITLE,
				"h1",
				"title"
		);
		Text errorCaptionText = buildText(
				message,
				"h2",
				"subtitle"
		);

		errorTextContainer.getChildren().addAll(errorTitleText, errorCaptionText);

		return errorTextContainer;
	}

	private Text buildText(String message, String... styleClasses) {
		Text text = new Text(message);
		text.getStyleClass().addAll(styleClasses);

		return text;
	}

}