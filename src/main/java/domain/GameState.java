package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    private final List<String> playerNames;
    private HashMap<Integer, List<String>> playerHands;
    private int startingPlayerIndex;
    private int currentPlayerIndex;
    private boolean isFaceUp;
    private boolean isGameOngoing;
    private boolean isValidPlay;
    private int currentDrawCount;
    private static final int playerThreeIndex = 3;
    private static final int playerFourIndex = 4;

    public GameState() {
        playerNames = List.of("STEVE", "MONKEY", "JENNY", "ELI", "GEORGE WASHINGTON");
        playerHands = getInitialHands();
        startingPlayerIndex = 0;
        currentPlayerIndex = 0;
        isFaceUp = false;
        isGameOngoing = false;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getStartingPlayerIndex() {
        return startingPlayerIndex;
    }

    public List<String> getCurrentPlayerHand() {
        return playerHands.get(getCurrentPlayerIndex());
    }

    public boolean getIsFaceUp() {
        return isFaceUp;
    }

    public void changeCurrentPlayerIndexAndSetIsFaceUpToFalse(int playerIndex) {
        currentPlayerIndex = playerIndex;
        isFaceUp = false;
    }

    public void setIsFaceUpToOpposite() {
        isFaceUp = !isFaceUp;
    }

    public boolean getIsGameOngoing() {
        return isGameOngoing;
    }

    public boolean getIsValidPlay() {
        return isValidPlay;
    }

    public boolean canEndTurn() {
        return currentDrawCount == 0;
    }

    public void startGame() {
        isGameOngoing = true;
        isValidPlay = false;
        currentDrawCount = 1;
    }

    private static HashMap<Integer, List<String>> getInitialHands() {
        return new HashMap<>(Map.of(
            0, List.of(
                    "DEFUSE", "ATTACK", "CAT", "CAT", "CLONE", "DRAW FROM THE BOTTOM"
                ),
            1, List.of(
                    "DEFUSE", "ATTACK", "CAT", "SWAP TOP AND BOTTOM", "CAT", "CAT"
                ),
            2, List.of(
                    "DEFUSE", "CAT", "CLONE", "RAISING HECK", "GODCAT", "CAT"
                ),
            playerThreeIndex, List.of(
                    "DEFUSE", "CAT", "CAT", "CAT", "CAT", "CAT"
                ),
            playerFourIndex, List.of(
                    "DEFUSE", "FEED THE DEAD", "ATTACK", "ATTACK", "CAT", "CAT"
                )
        ));
    }

}
