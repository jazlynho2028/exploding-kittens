package domain;

public class GameException extends Exception {
    private final String key;

    public GameException(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
