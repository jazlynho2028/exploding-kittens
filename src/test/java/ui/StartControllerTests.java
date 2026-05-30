package ui;

import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class StartControllerTests {

	@Test
	public void buildStartScene_called_success() {
		StartView view = EasyMock.createMock(StartView.class);
		Scene expectedScene = EasyMock.createMock(Scene.class);
		Runnable onEnglishPlay = EasyMock.createMock(Runnable.class);
		Runnable onSpanishPlay = EasyMock.createMock(Runnable.class);

		view.bindEnglishPlayButton(onEnglishPlay);
		EasyMock.expectLastCall();

		view.bindSpanishPlayButton(onSpanishPlay);
		EasyMock.expectLastCall();

		EasyMock.expect(view.createStartScene()).andReturn(expectedScene);

		EasyMock.replay(view);

		StartController controller = new StartController(view);
		controller.setOnEnglishPlay(onEnglishPlay);
		controller.setOnSpanishPlay(onSpanishPlay);

		Scene actualScene = controller.buildStartScene();
		assertSame(expectedScene, actualScene);

		EasyMock.verify(view);
	}

}
