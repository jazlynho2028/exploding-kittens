package ui;

import javafx.scene.Parent;

public class ErrorController {

    private final ErrorView view;

    public ErrorController() {
        this.view = new ErrorView();
    }

    public Parent getViewRoot() {
        return view.getRoot();
    }

}
