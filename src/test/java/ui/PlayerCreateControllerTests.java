package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerCreateControllerTests {

    private static final int PLAYER_COUNT_ONE = 1;
    private static final int PLAYER_COUNT_TWO = 2;
    private static final int PLAYER_COUNT_THREE = 3;
    private static final int PLAYER_COUNT_FOUR = 4;

    @Test
    public void onAddPlayer_CurrentZero_Success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);

        view.addPlayerField(PLAYER_COUNT_ONE);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_ONE, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_CurrentThree_Success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);

        PlayerCreateController controller = EasyMock.createMockBuilder(PlayerCreateController.class)
                .withConstructor(view)
                .createMock();

        view.addPlayerField(PLAYER_COUNT_ONE);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(PLAYER_COUNT_TWO);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(PLAYER_COUNT_THREE);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(PLAYER_COUNT_FOUR);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(true);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_CurrentFour_Failed() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        PlayerCreateController controller = EasyMock.createMockBuilder(PlayerCreateController.class)
                .withConstructor(view)
                .createMock();

        controller.setOnError(onError);

        for (int i = PLAYER_COUNT_ONE; i <= PLAYER_COUNT_FOUR; i++) {
            view.addPlayerField(i);
            EasyMock.expectLastCall();
            view.setAddPlayerButtonDisabled(i == PLAYER_COUNT_FOUR);
            EasyMock.expectLastCall();
        }

        onError.accept("You cannot have more than 4 players");
        EasyMock.expectLastCall();

        EasyMock.replay(view, onError);

        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(view, onError);
    }

    @Test
    public void onConfirmNames_OnePlayer_Failed() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);

        List<String> mockInputs = List.of("Alice");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        onError.accept("You need at least 2 players");
        EasyMock.expectLastCall();

        EasyMock.replay(view, onError, onSuccess);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.setOnError(onError);
        controller.setOnSuccess(onSuccess);

        controller.onConfirmNames();

        EasyMock.verify(view, onError, onSuccess);
    }

    @Test
    public void onConfirmNames_TwoPlayers_Success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);

        List<String> mockInputs = List.of("Alice", "Bob");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        onSuccess.run();
        EasyMock.expectLastCall();

        EasyMock.replay(view, onSuccess);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.setOnSuccess(onSuccess);

        controller.onConfirmNames();

        EasyMock.verify(view, onSuccess);

        List<String> confirmed = controller.getConfirmedNames();
        assertEquals(PLAYER_COUNT_TWO, confirmed.size());
        assertEquals("Alice", confirmed.get(0));
        assertEquals("Bob", confirmed.get(1));
    }

    @Test
    public void onConfirmNames_onSuccess_Error() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        List<String> mockInputs = List.of("Alice", "Bob", "Dave");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        String errorMsg = "Deck creation failed";
        onSuccess.run();
        EasyMock.expectLastCall().andThrow(new IllegalStateException(errorMsg));

        onError.accept(errorMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(view, onSuccess, onError);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.setOnSuccess(onSuccess);
        controller.setOnError(onError);

        controller.onConfirmNames();

        EasyMock.verify(view, onSuccess, onError);
    }
}