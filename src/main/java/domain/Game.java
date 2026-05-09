package domain;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameData {

	public Game() { }

	// GameData methods
	public List<String> getPlayerNames() {
		return new ArrayList<>();
	}

	public int getCurrentPlayerIndex() {
		return 0;
	}

	public int getStartingPlayerIndex() {
		return 0;
	}

	public List<String> getCurrentPlayerHand() {
		return new ArrayList<>();
	}

	public boolean getIsFaceUp() {
		return true;
	}

	public boolean isGameOngoing() {
		return true;
	}

	public boolean getIsBeforeDraw() {
		return true;
	}

	public boolean canPlaySelected() {
		return true;
	}

	public boolean canEndTurn() {
		return true;
	}

	public boolean canDraw() {
		return true;
	}

	public boolean isDrawPileEmpty() {
		return true;
	}

	// Mutable Game methods
	public void changeCurrentPlayerIndexAndSetIsFaceUpToFalse(int playerIndex) { }

	public void drawFromPile() { }

	public void setIsFaceUpToOpposite() { }

	public void setIsSelectedOfPlayerCardAtIndexToOpposite(int cardIndex) { }

	public void startGame() { }

}
