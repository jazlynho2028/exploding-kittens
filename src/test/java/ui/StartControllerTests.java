package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

public class StartControllerTests {

	@Test
	public void constructor_called_success() {
		StartView view = EasyMock.createMock(StartView.class);
		Runnable onEnglishPlay = EasyMock.anyObject(Runnable.class);
		Runnable onSpanishPlay = EasyMock.anyObject(Runnable.class);

		view.bindUI(onEnglishPlay, onSpanishPlay);
		EasyMock.expectLastCall();

		EasyMock.replay(view);

		new StartController(view);

		EasyMock.verify(view);
	}

	@Test
	public void onEnglishPlayButton_buttonPressed_success() {
		StartView view = EasyMock.createMock(StartView.class);
		Runnable onEnglishPlay = EasyMock.createMock(Runnable.class);

		onEnglishPlay.run();
		EasyMock.expectLastCall();

		StartController controller = new StartController(view);
		controller.setOnEnglishPlay(onEnglishPlay);

		EasyMock.replay(onEnglishPlay);

		controller.onEnglishPlayButton();

		EasyMock.verify(onEnglishPlay);
	}

	@Test
	public void onSpanishPlayButton_buttonPressed_success() {
		StartView view = EasyMock.createMock(StartView.class);
		Runnable onSpanishPlay = EasyMock.createMock(Runnable.class);

		onSpanishPlay.run();
		EasyMock.expectLastCall();

		StartController controller = new StartController(view);
		controller.setOnSpanishPlay(onSpanishPlay);

		EasyMock.replay(onSpanishPlay);

		controller.onSpanishPlayButton();

		EasyMock.verify(onSpanishPlay);
	}

}
