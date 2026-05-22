package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;
    private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    public StartController(AssetProvider assets) {
        this.view = new StartView(assets);
        this.onEnglishPlay = () -> { };
        this.onSpanishPlay = () -> { };

        bindUI();
    }

    // Fake constructor for tests to exclude UI view implementation
    StartController(StartView view) {
        this.view = view;
    }

    public void setOnEnglishPlay(Runnable onEnglishPlay) {
        this.onEnglishPlay = onEnglishPlay;
    }

    public void setOnSpanishPlay(Runnable onSpanishPlay) {
        this.onSpanishPlay = onSpanishPlay;
    }

    private void bindUI() {
        view.playButtonEnglish.setOnMouseClicked(e -> onEnglishPlayButton());
        view.playButtonSpanish.setOnMouseClicked(e -> onSpanishPlayButton());
    }

    void onEnglishPlayButton() {
        onEnglishPlay.run();
    }

    void onSpanishPlayButton() {
        onSpanishPlay.run();
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}
