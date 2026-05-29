package domain;

import java.util.List;

public interface GameData {

	List<String> getPlayerNames();

	int getCurrentPlayerIndex();

	int getStartingPlayerIndex();

	List<String> getCurrentPlayerHandIds();

	boolean getIsFaceUp();

	boolean getIsGameOngoing();

	boolean getIsBeforeDraw();

	boolean canPlaySelected();

	boolean canEndTurn();

	boolean getCanDraw();

	boolean isDrawPileEmpty();

}