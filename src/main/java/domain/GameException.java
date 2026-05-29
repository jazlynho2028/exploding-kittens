package domain;

public class GameException extends RuntimeException {
    private final String key;

    public GameException(String key) {
        super(key);
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}