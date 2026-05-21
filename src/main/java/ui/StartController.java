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

    private void onEnglishPlayButton() {
        onEnglishPlay.run();
    }

    private void onSpanishPlayButton() {
        onSpanishPlay.run();
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}
