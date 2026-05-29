package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;

public class Game {

    @SuppressFBWarnings(
            value = {"EI_EXPOSE_REP2", "CT_CONSTRUCTOR_THROW"},
            justification = "EI_EXPOSE_REP2: TurnManager is injected by for testability. " +
                    "Defensive copy is not desired in this context. " +
                    "CT_CONSTRUCTOR_THROW: Game is an internal domain object. Finalizer " +
                    "attack is not a concern."
    )
    public Game(List<Player> players, Deck drawPile,
                Deck discardPile, TurnManager turnManager) { }

    private void populatePlayerHand() { }

    public void startGame() { }

    public List<String> getPlayerNames() {
        return List.of();
    }

    public int getCurrentPlayerIndex() {
        return 0;
    }

    public int getStartingPlayerIndex() {
        return 0;
    }

    public List<String> getCurrentPlayerHandIds() {
        return List.of();
    }

    private Player getCurrentPlayer() {
        return new Player("");
    }

    public boolean canPlaySelected() {
        return true;
    }

    public boolean canEndTurn() {
        return true;
    }

    public boolean isDrawPileEmpty() {
        return true;
    }

    public boolean getIsGameOngoing() {
        return true;
    }

    public boolean getCanDraw() {
        return true;
    }

    public boolean getIsFaceUp() {
        return true;
    }

    public void changeCurrentPlayerIndex(int newPlayerIndex) { }

    public void setFaceUpToFalse() { }

    public void drawFromPile() { }

    public void toggleFaceUp() { }

    public void toggleSelectedPlayerCardAt(int handCardIndex) { }

    public void advanceTurn() { }
}
