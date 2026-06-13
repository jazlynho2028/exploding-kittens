package domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CatomicBombTests {

    private static final int SMALL_GAME_NUM_PLAYERS = 2;
    private static final int INITIAL_PLAYER_INDEX = 0;
    private static final int DEFAULT_DECK_ADDITION = 5;
    private static final int DRAW_COUNT_POST_TURN_END = 1;
    private static final int SINGLE_CARD_OFFSET = 1;

    private List<Player> players;
    private Deck drawPile;
    private Deck discardPile;
    private TurnManager turnManager;
    private Game game;
    private Player targetPlayer;

    private void setUpStartGame(int numPlayers, int targetPlayerIndex) {
        players = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + i));
        }
        targetPlayer = players.get(targetPlayerIndex);

        Random random = new Random();
        drawPile = new Deck(new ArrayDeque<>(), random);
        discardPile = new Deck(new ArrayDeque<>(), random);
        turnManager = new TurnManager(numPlayers);
        game = new Game(players, drawPile, discardPile, turnManager);
        game.startGame();
    }

    private void addCardsToDrawPile(int numCards) {
        for (int i = 0; i < numCards; i++) {
            String cardId = DeckBuilder.createCardId(CardType.SEE_THE_FUTURE, i + SINGLE_CARD_OFFSET);
            drawPile.addCardToTop(new Card(cardId, CardType.SEE_THE_FUTURE));
        }
    }

    private void addCatomicBombToHand(Player player) {
        String cardId = DeckBuilder.createCardId(CardType.CATOMIC_BOMB, SINGLE_CARD_OFFSET);
        player.addCardToHand(new Card(cardId, CardType.CATOMIC_BOMB));
    }

    private void selectCatomicBomb(Player player) {
        for (int i = 0; i < player.getHand().size(); i++) {
            if (player.getHand().get(i).getType() == CardType.CATOMIC_BOMB) {
                player.toggleSelectedHandCardAt(i);
                return;
            }
        }
    }

    @Test
    public void catomicBomb_normalTurn_sortsKittensToTopAndEndsTurn() {
        setUpStartGame(SMALL_GAME_NUM_PLAYERS, INITIAL_PLAYER_INDEX);

        addCardsToDrawPile(DEFAULT_DECK_ADDITION);
        addCatomicBombToHand(targetPlayer);

        selectCatomicBomb(targetPlayer);
        game.playSelectedCards();

        int expectedNextPlayer = INITIAL_PLAYER_INDEX + 1;

        assertEquals(DRAW_COUNT_POST_TURN_END, turnManager.getDrawCount());
        assertEquals(expectedNextPlayer, game.getCurrentPlayerIndex());
        assertEquals(CardType.EXPLODING_KITTEN, drawPile.peekTop().getType());
    }

    @Test
    public void catomicBomb_underAttack_sortsKittensAndDecrementsCount() {
        setUpStartGame(SMALL_GAME_NUM_PLAYERS, INITIAL_PLAYER_INDEX);

        addCardsToDrawPile(DEFAULT_DECK_ADDITION);
        addCatomicBombToHand(targetPlayer);

        int initialDrawCount = 3;
        turnManager.setDrawCount(initialDrawCount);

        selectCatomicBomb(targetPlayer);
        game.playSelectedCards();

        int expectedDrawCount = initialDrawCount - 1;

        assertEquals(expectedDrawCount, turnManager.getDrawCount());
        assertEquals(INITIAL_PLAYER_INDEX, game.getCurrentPlayerIndex());
        assertEquals(CardType.EXPLODING_KITTEN, drawPile.peekTop().getType());
    }

    @Test
    public void catomicBomb_underAttack_nextDrawIsExplodingKitten() {
        setUpStartGame(SMALL_GAME_NUM_PLAYERS, INITIAL_PLAYER_INDEX);

        addCardsToDrawPile(DEFAULT_DECK_ADDITION);
        addCatomicBombToHand(targetPlayer);

        int attackCount = GameConstants.ATTACK_DRAW_COUNT;
        turnManager.setDrawCount(attackCount);

        selectCatomicBomb(targetPlayer);
        game.playSelectedCards();

        int expectedDrawCount = attackCount - 1;
        assertEquals(expectedDrawCount, turnManager.getDrawCount());

        Card drawnCard = game.drawFromPile();
        assertEquals(CardType.EXPLODING_KITTEN, drawnCard.getType());
    }

    @Test
    public void catomicBomb_fourPlayers_allKittensConsolidatedAtTop() {
        int numPlayers = GameConstants.MAX_PLAYERS;
        setUpStartGame(numPlayers, INITIAL_PLAYER_INDEX);

        addCardsToDrawPile(DEFAULT_DECK_ADDITION);
        addCatomicBombToHand(targetPlayer);

        selectCatomicBomb(targetPlayer);
        game.playSelectedCards();

        assertEquals(DRAW_COUNT_POST_TURN_END, turnManager.getDrawCount());

        int numExpectedKittens = numPlayers - 1;
        List<Card> topCards = drawPile.peekTopNCards(numExpectedKittens);
        for (Card card : topCards) {
            assertEquals(CardType.EXPLODING_KITTEN, card.getType());
        }
    }

}