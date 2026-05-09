package ui;

import javafx.scene.Scene;

public class ErrorController {

	private final ErrorView view;

	public ErrorController(String message) {
		this.view = new ErrorView(message);
	}

	public Scene getErrorScene() {
		return view.createErrorScene();
	}

}