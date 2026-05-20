package ui;

import javafx.scene.Scene;

public class StartController {

    private final StartView view;
    private Runnable onPlay;

    public StartController(AssetProvider assetProvider) {
        this.view = new StartView(assetProvider);
        this.onPlay = () -> { };

        buildAndBindUI();
    }

    public void setOnPlay(Runnable onPlay) {
        this.onPlay = onPlay;
    }

    private void buildAndBindUI() {
        bindUI();
    }

    private void bindUI() {
        view.playButton.setOnMouseClicked(e -> onPlayButton());
    }

    private void onPlayButton() {
        System.out.println("START GAME BUTTON CLICKED");
        onPlay.run();
    }

    public Scene getStartScene() {
        return view.createStartScene();
    }
}