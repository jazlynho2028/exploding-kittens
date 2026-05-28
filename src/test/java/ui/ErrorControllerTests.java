package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class ErrorControllerTests {

	@Test
	public void constructor_called_success() {
		ErrorView view = EasyMock.createMock(ErrorView.class);

		view.bindUI(EasyMock.anyObject(Runnable.class));
		EasyMock.expectLastCall();

		EasyMock.replay(view);

		new ErrorController(view);

		EasyMock.verify(view);
	}

	@Test
	public void onRestartButton_buttonPressed_success() {
		ErrorView view = EasyMock.createMock(ErrorView.class);
		Runnable onRestart = EasyMock.createMock(Runnable.class);

		onRestart.run();
		EasyMock.expectLastCall();

		EasyMock.replay(onRestart);

		ErrorController controller = new ErrorController(view);
		controller.setOnRestart(onRestart);

		controller.onRestartButton();

		EasyMock.verify(onRestart);
	}

}
