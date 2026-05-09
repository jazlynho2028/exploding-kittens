package domain;

import java.util.*;

public class GameState implements GameStateData {

    // initialized before game starts
    private final List<String> playerNames;
    private Map<Integer, List<String>> playerHands;
    private final int startingPlayerIndex;
    private int currentPlayerIndex;
    private boolean isFaceUp;

    enum GamePhase {
        NOT_STARTED,
        ONGOING,
        FINISHED
    }

    private GamePhase gamePhase;
    private final Deque<String> drawPile;

    // initialized after game starts
    private boolean isBeforeDraw;
    private List<String> selectedHandCards;
    private int currentDrawCount;

    public GameState() {
        playerNames = List.of("STEVE", "MONKEY", "JENNY", "ELI");
        playerHands = getInitialHands();
        startingPlayerIndex = 0;
        currentPlayerIndex = 0;
        isFaceUp = false;
        gamePhase = GamePhase.NOT_STARTED;
        drawPile = new ArrayDeque<>(
                List.of("ATTACK", "SEE THE FUTURE", "NOPE")
        );
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

    public boolean isGameOngoing() {
        return gamePhase == GamePhase.ONGOING;
    }

    public boolean getIsBeforeDraw() {
        return isBeforeDraw;
    }

    public boolean canPlaySelected() {
        return isGameOngoing() &&
                isBeforeDraw &&
                isValidHand(selectedHandCards);
    }

    private boolean isValidHand(List<String> selectedHandCards) {
        return !selectedHandCards.isEmpty() && (
                isOneOfAKind() ||
                isNOfAKind(2) ||
                isNOfAKind(THREE));
    }

    private boolean isOneOfAKind() {
        String cardName = selectedHandCards.get(0);

        boolean isOneCard = selectedHandCards.size() == 1;
        boolean isCatCard = Objects.equals(cardName, "CAT");
        boolean isDefuseCard = Objects.equals(cardName, "DEFUSE");

        return isOneCard && !isCatCard && !isDefuseCard;
    }

    private boolean isNOfAKind(int n) {
        boolean isNCards = selectedHandCards.size() == n;
        return isNCards && isAllSameCards(selectedHandCards);
    }

    private boolean isAllSameCards(List<String> cards) {
        return new HashSet<>(cards).size() <= 1;
    }

    public boolean canEndTurn() {
        return isGameOngoing() && (currentDrawCount <= 0);
    }

    public void startGame() {
        gamePhase = GamePhase.ONGOING;
        selectedHandCards = new ArrayList<>();
        currentDrawCount = 1;
        isBeforeDraw = true;
    }

    public boolean canDraw() {
        return isGameOngoing() && (currentDrawCount > 0);
    }

    private void addCardToCurrentPlayerHand(String cardName) {
        getCurrentPlayerHand().add(cardName);
    }

    public void addToSelectedHandCards(String cardName) {
        selectedHandCards.add(cardName);
    }

    public void removeFromSelectedHandCards(String cardName) {
        selectedHandCards.remove(cardName);
    }


    public void drawFromPile() {
        String drawnCardName = drawPile.pollFirst();
        addCardToCurrentPlayerHand(drawnCardName);

        currentDrawCount--;
        isBeforeDraw = false;
    }

    public boolean isDrawPileEmpty() {
        return drawPile.isEmpty();
    }

    private static Map<Integer, List<String>> getInitialHands() {
        return new HashMap<>(Map.of(
            0, new ArrayList<>(List.of(
                    "DEFUSE", "ATTACK", "CAT", "CAT", "CLONE", "DRAW FROM THE BOTTOM"
                )),
            1, new ArrayList<>(List.of(
                    "DEFUSE", "ATTACK", "CAT", "SWAP TOP AND BOTTOM", "CAT", "CAT"
                )),
            2, new ArrayList<>(List.of(
                    "DEFUSE", "CAT", "CLONE", "RAISING HECK", "GODCAT", "CAT"
                )),
                THREE, new ArrayList<>(List.of(
                    "DEFUSE", "CAT", "CAT", "CAT", "CAT", "CAT"
                ))
        ));
    }

    private static final int THREE = 3;

}
