package domain;

import java.util.ArrayList;
import java.util.List;

public class Game implements GameData {


    public Game(List<String> playerNames) { }

    // GameData methods
    public List<String> getPlayerNames() {
        ArrayList<String> list = new ArrayList<>();
        list.add("player1");
        list.add("player2");
        list.add("player3");
        return list;
    }

    public int getCurrentPlayerIndex() {
        return 0;
    }

    public int getStartingPlayerIndex() {
        return 0;
    }

    public List<String> getCurrentPlayerHandIds() {
        ArrayList<String> list = new ArrayList<>();
        list.add("EXPLODINGKITTEN_1");
        list.add("EXPLODINGKITTEN_2");
        list.add("EXPLODINGKITTEN_3");
        list.add("DEFUSE_1");
        list.add("DEFUSE_2");
        list.add("DEFUSE_3");
        list.add("DEFUSE_4");
        list.add("DEFUSE_5");
        list.add("ATTACK_1");
        list.add("ATTACK_2");
        list.add("ATTACK_3");
        list.add("SHUFFLE_1");
        list.add("SHUFFLE_2");
        list.add("SHUFFLE_3");
        list.add("SHUFFLE_4");
        list.add("SKIP_1");
        list.add("SKIP_2");
        list.add("SKIP_3");
        list.add("SEETHEFUTURE_1");
        list.add("SEETHEFUTURE_2");
        list.add("SEETHEFUTURE_3");
        list.add("SEETHEFUTURE_4");
        list.add("FERALCAT_1");
        list.add("FERALCAT_2");
        list.add("FERALCAT_3");
        list.add("FERALCAT_4");
        list.add("CATCARD_11");
        list.add("CATCARD_12");
        list.add("CATCARD_13");
        list.add("CATCARD_14");
        list.add("CATCARD_21");
        list.add("CATCARD_22");
        list.add("CATCARD_23");
        list.add("CATCARD_24");
        list.add("CATCARD_31");
        list.add("CATCARD_32");
        list.add("CATCARD_33");
        list.add("CATCARD_34");
        list.add("CATCARD_41");
        list.add("CATCARD_42");
        list.add("CATCARD_43");
        list.add("CATCARD_44");
        list.add("CATOMICBOMB_1");
        list.add("SUPERSKIP_1");
        list.add("SUPERSKIP_2");
        list.add("GODCAT_1");
        list.add("CLONE_1");
        list.add("CLONE_2");
        list.add("CLONE_3");
        list.add("SWAPTOPANDBOTTOM_1");
        list.add("SWAPTOPANDBOTTOM_2");
        list.add("SWAPTOPANDBOTTOM_3");
        list.add("DRAWFROMTHEBOTTOM_1");
        list.add("DRAWFROMTHEBOTTOM_2");
        list.add("DRAWFROMTHEBOTTOM_3");
        list.add("TARGETEDATTACK_1");
        list.add("TARGETEDATTACK_2");
        list.add("TARGETEDATTACK_3");
        list.add("TARGETEDATTACK_4");
        list.add("WINNERWINNERCATNIPDINNER_1");
        list.add("RAGEBAIT_1");
        list.add("RECYCLE_1");
        list.add("DOUBLEUP_1");
        list.add("MILDDRAW_1");
        return list;
    }

    public boolean getIsFaceUp() {
        return true;
    }

    public boolean isGameOngoing() {
        return false;
    }

    public boolean getIsBeforeDraw() {
        return true;
    }

    public boolean canPlaySelected() {
        return true;
    }

    public boolean canEndTurn() {
        return false;
    }

    public boolean canDraw() {
        return false;
    }

    public boolean isDrawPileEmpty() {
        return false;
    }

    // Mutable Game methods
    public void changeCurrentPlayerIndexAndSetIsFaceUpToFalse(int playerIndex) { }

    public void drawFromPile() { }

    public void setIsFaceUpToOpposite() { }

    public void setIsSelectedOfPlayerCardAtIndexToOpposite(int cardIndex) { }

    public void startGame() { }

}