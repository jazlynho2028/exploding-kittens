package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;
    private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    public StartController(AssetProvider assetProvider) {
        this.view = new StartView(assetProvider);
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
        System.out.println("ENGLISH START GAME BUTTON CLICKED");
        onEnglishPlay.run();
    }

    private void onSpanishPlayButton() {
        System.out.println("SPANISH START GAME BUTTON CLICKED");
        onSpanishPlay.run();
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}
