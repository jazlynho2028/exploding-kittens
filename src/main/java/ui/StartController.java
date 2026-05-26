package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;

	private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    public StartController(StartView view) {
        this.view = view;
    }

    public Scene buildStartScene() {
        view.bindUI(
                this::onEnglishPlayButton,
                this::onSpanishPlayButton
        );

        return view.createStartScene();
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
