package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class StartControllerTests {

	@Test
	public void onEnglishPlayButton_buttonPressed_success() {
		StartView view = EasyMock.createMock(StartView.class);
		Runnable onEnglishPlay = EasyMock.createMock(Runnable.class);

		onEnglishPlay.run();
		EasyMock.expectLastCall();

		StartController controller = new StartController(view);
		controller.setOnEnglishPlay(onEnglishPlay);

		EasyMock.replay(view, onEnglishPlay);

		controller.onEnglishPlayButton();

		EasyMock.verify(view, onEnglishPlay);
	}

}
