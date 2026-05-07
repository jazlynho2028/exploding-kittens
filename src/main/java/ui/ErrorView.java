package ui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ErrorView {

    private final StackPane root;

    public ErrorView() {
        root = new StackPane();

        buildUI();
    }

    public Parent getRoot() {
        return root;
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
                "h1"
        );
        Text errorCaptionText = buildText(
                UIConstants.ERROR_MESSAGE_CAPTION,
                "h2"
        );

        errorTextContainer.getChildren().addAll(errorTitleText, errorCaptionText);

        return errorTextContainer;
    }

    private Text buildText(String message, String styleClass) {
        Text text = new Text(message);
        text.getStyleClass().add(styleClass);

        return text;
    }

}
