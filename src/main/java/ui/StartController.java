package ui;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.Scene;

public class StartController {

    private final StartView view;

	private Runnable onEnglishPlay;
    private Runnable onSpanishPlay;

    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP2",
            justification = "View is injected by for compromise between MVC pattern and " +
                    "testability, defensive copy is not applicable for JavaFX components"
    )
    public StartController(StartView view) {
        this.view = view;
    }

    public Scene buildStartScene() {
        view.bindEnglishPlayButton(onEnglishPlay);
        view.bindSpanishPlayButton(onSpanishPlay);

        return view.createStartScene();
    }

    public void setOnEnglishPlay(Runnable onEnglishPlay) {
        this.onEnglishPlay = onEnglishPlay;
    }

    public void setOnSpanishPlay(Runnable onSpanishPlay) {
        this.onSpanishPlay = onSpanishPlay;
    }
}
