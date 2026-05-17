package ui;

import domain.Game;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerCreateController {
    private final PlayerCreateView view;
    private final List<TextField> playerFields = new ArrayList<>();

    private List<String> confirmedNames;
    private Consumer<String> onError;
    private Runnable onSuccess;
    private Runnable onBack;

    private static final int MAX_PLAYERS = 4;

    public PlayerCreateController(AssetProvider assets) {
        this.view = new PlayerCreateView(assets);
        this.onError = message -> { };

        buildAndBindUI();
    }

    public void setOnError(Consumer<String> onError) {
        this.onError = onError;
    }

    public void setOnSuccess(Runnable onSuccess) {
        this.onSuccess = onSuccess;
    }

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }

    private void buildAndBindUI() {
        addPlayerFieldEntry();
        addPlayerFieldEntry();
        bindUI();
    }

    private void bindUI() {
        view.addPlayerButton.setOnMouseClicked(e -> onAddPlayer());
        view.confirmButton.setOnMouseClicked(e -> onConfirmNames());
        view.backButton.setOnMouseClicked(e -> onBack.run());
    }

    private void onAddPlayer() {
        addPlayerFieldEntry();
    }

    private void addPlayerFieldEntry() {
        if (playerFields.size() >= MAX_PLAYERS) {
            return;
        }

        int visualIndex = playerFields.size() + 1;
        TextField field = view.createPlayerTextField(visualIndex);
        playerFields.add(field);

        view.updatePlayerFieldsDisplay(playerFields);
        view.setAddPlayerButtonDisabled(playerFields.size() >= MAX_PLAYERS);
    }

    private void onConfirmNames() {
        List<String> names = new ArrayList<>();
        for (TextField field : playerFields) {
            String input = field.getText();
            if (input != null && !input.isBlank()) {
                names.add(input.trim());
            }
        }

        if (names.size() < 2) {
            onError.accept("You need at least 2 players to start!");
            return;
        }

        this.confirmedNames = names;

        try {
            if (onSuccess != null) {
                onSuccess.run();
            }
        } catch (Exception e) {
            onError.accept("Error initializing game: " + e.getMessage());
        }
    }

    public List<String> getConfirmedNames() {
        return new ArrayList<>(confirmedNames);
    }

    public Scene getPlayerCreateScene() {
        return view.createPlayerCreateScene();
    }
}