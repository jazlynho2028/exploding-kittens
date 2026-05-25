package ui;

public class ErrorController {

	private Runnable onRestart;

	public ErrorController(ErrorView view) {
		view.bindUI(this::onRestartButton);
	}

	void onRestartButton() {
		onRestart.run();
	}

	public void setOnRestart(Runnable onRestart) {
		this.onRestart = onRestart;
	}

}