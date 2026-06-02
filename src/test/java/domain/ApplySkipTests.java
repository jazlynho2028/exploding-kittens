package domain;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplySkipTests {
    private Game createStartedGame(List<Player> players,
                                   Deck drawPile,
                                   Deck discardPile,
                                   TurnManager turnManager) {

        for (Player player : players) {
            player.addCardToHand(EasyMock.anyObject(Card.class));
            EasyMock.expectLastCall().times(GameConstants.STARTING_HAND_SIZE);
        }

        EasyMock.expect(drawPile.removeTop())
                .andReturn(new Card("c1", CardType.ATTACK))
                .times((GameConstants.STARTING_HAND_SIZE - 1) * players.size());

        drawPile.addCard(EasyMock.anyObject(Card.class));
        EasyMock.expectLastCall().anyTimes();

        drawPile.shuffle();
        EasyMock.expectLastCall().anyTimes();

        EasyMock.replay(players.toArray());
        EasyMock.replay(drawPile, discardPile);

        Game game = new Game(players, drawPile, discardPile, turnManager);
        game.startGame();

        return game;
    }

}
