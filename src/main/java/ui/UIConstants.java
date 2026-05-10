package ui;

import java.util.Arrays;
import java.util.HashMap;

public final class UIConstants {

    private UIConstants() {}

    public static final String TITLE = "Exploding Kittens";
    public static final String PLAYER_HEADER_CAPTION = "CLICK TO SEE EACH PLAYER'S CARDS";
    public static final String DRAW_PILE_CAPTION = "CLICK TO DRAW";
    public static final String DISCARD_PILE_CAPTION = "DISCARD";
    public static final String SHOW_HAND_LABEL = "REVEAL";
    public static final String HAND_CAPTION = "SCROLL TO SEE ALL CARDS";
    public static final String START_GAME_LABEL = "START GAME";

    public static final int INIT_NUM_CARDS_PER_HAND = 6;

    // PLAYER_NAMES, INITIAL_PLAYER_HANDS, getInitialHands are temporary and will be eventually fetched from model
    public static final String[] PLAYER_NAMES = {
            "STEVE", "MONKEY", "JENNY", "ELI", "GEORGE WASHINGTON"
    };
    public static final HashMap<String, String[]> INITIAL_PLAYER_HANDS = getInitialHands(PLAYER_NAMES);
    private static HashMap<String, String[]> getInitialHands(String[] playerNames) {
        HashMap<String, String[]> initialPlayerHands = new HashMap<>();

        for (String playerName : playerNames) {
            String[] hand = new String[INIT_NUM_CARDS_PER_HAND];
            Arrays.fill(hand, "DEFUSE");

            initialPlayerHands.put(playerName, hand);
        }

        return initialPlayerHands;
    }

    public static final int SCENE_WIDTH = 1280;
    public static final int SCENE_HEIGHT = 832;

    public static final int CARD_BACK_ICON_WIDTH = 40;
    public static final int SMALL_ICON_SIZE = 16;

    public static final int MIN_PERCENT = 0;
    public static final int MAX_PERCENT = 100;

    public static final String YELLOW_1 = "#F0EAD3";
    public static final String YELLOW_3 = "#FFEC2B";
    public static final String YELLOW_4 = "#F6D300";
    public static final String ORANGE_3 = "#FD6738";
    public static final String RED_3 = "#DF2126";
    public static final String MAGENTA_3 = "#B3294C";
    public static final String MAGENTA_4 = "#541931";

}
