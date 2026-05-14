package ui;

import javafx.scene.Scene;

public class ErrorController {

	private final ErrorView view;

	public ErrorController(AssetProvider assetProvider, String message) {
		this.view = new ErrorView(assetProvider, message);
	}

	public Scene getErrorScene() {
		return view.createErrorScene();
	}

}