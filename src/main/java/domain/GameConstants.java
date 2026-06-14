package domain;

import java.util.List;

public final class GameConstants {

	private GameConstants() { }

	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;
	public static final int MAX_PLAYER_INDEX = 3;

	public static final int STARTING_HAND_SIZE = 6;
	public static final int STARTING_PLAYER_INDEX = 0;

	public static final int ONE_CARD = 1;
	public static final int TWO_CARDS = 2;
	public static final int THREE_CARDS = 3;
	public static final int FOUR_CARDS = 4;

	public static final int NUM_MILD_SHUFFLE_IN_GAME = 1;
	public static final int MILD_SHUFFLE_SHUFFLE_COUNT = 3;
	public static final int NUM_DRAW_COUNT_AFTER_SUPER_SKIP = 0;
	public static final int ATTACK_DRAW_COUNT = 2;
	public static final int NUM_GODCAT_IN_GAME = 1;
	public static final int NUM_WINNER_WINNER_CATNIP_DINNER_IN_GAME = 1;
	public static final int NUM_RAGEBAIT_IN_GAME = 1;
	public static final int NUM_RECYCLE_IN_GAME = 1;
	public static final int NUM_DOUBLE_UP_IN_GAME = 1;
	public static final int NUM_CATOMIC_BOMB_IN_GAME = 1;
	public static final int NUM_SUPER_SKIP_IN_GAME = 2;
	public static final int NUM_ATTACK_IN_GAME = 3;
	public static final int NUM_SKIP_IN_GAME = 3;
	public static final int NUM_CLONE_IN_GAME = 3;
	public static final int NUM_SWAP_TOP_AND_BOTTOM_IN_GAME = 3;
	public static final int NUM_DRAW_FROM_THE_BOTTOM_IN_GAME = 3;
	public static final int NUM_FERAL_CAT_IN_GAME = 4;
	public static final int NUM_SEE_THE_FUTURE_IN_GAME = 4;
	public static final int NUM_SHUFFLE_IN_GAME = 4;
	public static final int NUM_TARGETED_ATTACK_IN_GAME = 4;
	public static final int NUM_CAT_CARD_IN_GAME = 4;
	public static final int NUM_DEFUSES_IN_GAME = 5;

	public static final int SEE_THE_FUTURE_PEEK_COUNT = 2;
	public static final int WINNER_WINNER_REQUIRED_ROUNDS = 4;

	public static final List<CardType> CONDITIONAL_PLAY_CARDTYPES = List.of(
			CardType.DEFUSE,
			CardType.EXPLODING_KITTEN,
			CardType.CAT_CARD_1,
			CardType.CAT_CARD_2,
			CardType.CAT_CARD_3,
			CardType.CAT_CARD_4,
			CardType.FERAL_CAT
	);

	public static final List<CardType> SELECTABLE_CARDTYPE_OPTIONS = List.of(
			CardType.ATTACK,
			CardType.SHUFFLE,
			CardType.SKIP,
			CardType.SEE_THE_FUTURE,
			CardType.CATOMIC_BOMB,
			CardType.SUPER_SKIP,
			CardType.CLONE,
			CardType.SWAP_TOP_AND_BOTTOM,
			CardType.DRAW_FROM_THE_BOTTOM,
			CardType.TARGETED_ATTACK,
			CardType.WINNER_WINNER_CATNIP_DINNER,
			CardType.RAGEBAIT,
			CardType.RECYCLE,
			CardType.DOUBLE_UP,
			CardType.MILD_SHUFFLE
	);

}