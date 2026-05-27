package ui;

import java.util.function.Consumer;

public class ErrorHandler {

	private ErrorHandler() { }

	public static void attempt(Consumer<String> onError, Runnable action) {
		try {
			action.run();
		}
		catch (Exception e) {
			onError.accept(e.getMessage());
		}
	}

}
