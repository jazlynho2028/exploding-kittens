package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

public class ErrorController {

	private final ErrorView view;
	private Runnable onRestart;

	@SuppressFBWarnings(
			value = "EI_EXPOSE_REP2",
			justification = "View is injected by for compromise between MVC pattern and " +
					"testability, defensive copy is not applicable for JavaFX components"
	)
	public ErrorController(ErrorView view) {
		this.view = view;
		this.onRestart = () -> { };
	}

	public Scene buildErrorScene() {
		view.bindRestartButton(onRestart);

		return view.createErrorScene();
	}

	public void setOnRestart(Runnable handler) {
		onRestart = handler;
	}

}