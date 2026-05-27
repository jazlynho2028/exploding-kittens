package ui;

import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class ErrorControllerTests {

	@Test
	public void buildErrorScene_called_success() {
		ErrorView view = EasyMock.createMock(ErrorView.class);
		Scene expectedScene = EasyMock.createMock(Scene.class);
		Runnable onRestart = EasyMock.createMock(Runnable.class);

		view.bindRestartButton(onRestart);
		EasyMock.expectLastCall();

		EasyMock.expect(view.createErrorScene()).andReturn(expectedScene);

		EasyMock.replay(view);

		ErrorController controller = new ErrorController(view);
		controller.setOnRestart(onRestart);

		Scene actualScene = controller.buildErrorScene();
		assertSame(expectedScene, actualScene);

		EasyMock.verify(view);
	}

}
