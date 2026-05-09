package domain;

import java.util.List;

public interface GameStateData {

	List<String> getPlayerNames();

	int getCurrentPlayerIndex();

	int getStartingPlayerIndex();

	List<String> getCurrentPlayerHand();

	boolean getIsFaceUp();

	boolean isGameOngoing();

	boolean getIsBeforeDraw();

	boolean canPlaySelected();

	boolean canEndTurn();

	boolean canDraw();

	boolean isDrawPileEmpty();

}
