package domain;

import java.util.ResourceBundle;

public class GameException extends RuntimeException{
    private final String key;

    public GameException(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
