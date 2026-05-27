package ui;

import javafx.scene.Scene;

public class ErrorController {

	private final ErrorView view;
	private Runnable onRestart;

	public ErrorController(ErrorView view) {
		this.view = view;
	}

	public Scene buildErrorScene() {
		view.bindRestartButton(onRestart);

		return view.createErrorScene();
	}

	public void setOnRestart(Runnable onRestart) {
		this.onRestart = onRestart;
	}

}