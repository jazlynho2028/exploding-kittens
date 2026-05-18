package domain;

import java.util.List;

public interface GameData {

	List<String> getPlayerNames();

	int getCurrentPlayerIndex();

	int getStartingPlayerIndex();

	List<String> getCurrentPlayerHandIds();

	boolean getIsFaceUp();

	boolean isGameOngoing();

	boolean getIsBeforeDraw();

	boolean canPlaySelected();

	boolean canEndTurn();

	boolean canDraw();

	boolean isDrawPileEmpty();

}