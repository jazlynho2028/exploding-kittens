package ui;

import javafx.scene.Scene;

public class ErrorController {

	private final ErrorView view;
	private Runnable onRestart;

	public ErrorController(ErrorView view) {
		this.view = view;
	}

	public Scene buildErrorScene() {
		view.bindUI(this::onRestartButton);

		return view.createErrorScene();
	}

	void onRestartButton() {
		onRestart.run();
	}

	public void setOnRestart(Runnable onRestart) {
		this.onRestart = onRestart;
	}

}