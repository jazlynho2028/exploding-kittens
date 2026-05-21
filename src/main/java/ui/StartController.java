package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;
    private final AssetProvider assets;
    private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    public StartController(AssetProvider assets) {
        this.view = new StartView(assets);
        this.assets = assets;
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
        System.out.println(assets.getString("console.playEnglish"));
        onEnglishPlay.run();
    }

    private void onSpanishPlayButton() {
        System.out.println(assets.getString("console.playSpanish"));
        onSpanishPlay.run();
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }

}
