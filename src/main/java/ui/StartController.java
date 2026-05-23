package ui;

public class StartController {

	private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    public StartController(StartView view) {
		view.bindUI(
                this::onEnglishPlayButton,
                this::onSpanishPlayButton
        );
    }

    public void setOnEnglishPlay(Runnable onEnglishPlay) {
        this.onEnglishPlay = onEnglishPlay;
    }

    public void setOnSpanishPlay(Runnable onSpanishPlay) {
        this.onSpanishPlay = onSpanishPlay;
    }

    void onEnglishPlayButton() {
        onEnglishPlay.run();
    }

    void onSpanishPlayButton() {
        onSpanishPlay.run();
    }

}
