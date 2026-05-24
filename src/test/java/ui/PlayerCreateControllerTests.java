package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PlayerCreateControllerTests {

    private static final int PLAYER_COUNT_TWO = 2;
    private static final int PLAYER_COUNT_THREE = 3;
    private static final int PLAYER_COUNT_FOUR = 4;

    private AssetProvider assets;
    private PlayerCreateView view;

    @BeforeEach
    public void setUpExpectations() {
        assets = EasyMock.createMock(AssetProvider.class);
        view = EasyMock.createMock(PlayerCreateView.class);

        view.bindUI(
                EasyMock.anyObject(Runnable.class),
                EasyMock.anyObject(Runnable.class),
                EasyMock.anyObject(Runnable.class)
        );
        EasyMock.expectLastCall();
    }

    @Test
    public void constructor_called_success() {
        String expectedMsg = "You need at least 2 players.";
        EasyMock.expect(assets.getString("error.minPlayers")).andReturn(expectedMsg);
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(new ArrayList<>());

        EasyMock.replay(assets, view);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        int playerNumbers = controller.getPlayerNumbers();

        assertDoesNotThrow(controller::onConfirmNames);
        assertEquals(PLAYER_COUNT_TWO, playerNumbers);

        EasyMock.verify(assets, view);
    }

    @Test
    public void onAddPlayer_CurrentTwo_Success() {
        view.addPlayerField(PLAYER_COUNT_THREE);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_THREE, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_CurrentThree_Success() {
        view.addPlayerField(PLAYER_COUNT_THREE);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(PLAYER_COUNT_FOUR);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(true);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.onAddPlayer();
        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_CurrentFour_Failed() {
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        view.addPlayerField(PLAYER_COUNT_THREE);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(PLAYER_COUNT_FOUR);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(true);
        EasyMock.expectLastCall();

        String expectedMsg = "You cannot have more than 4 players.";
        EasyMock.expect(assets.getString("error.maxPlayers")).andReturn(
                expectedMsg
        );

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onError);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnError(onError);
        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(assets, view, onError);
    }

    @Test
    public void onConfirmNames_OnePlayer_Failed() {
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        List<String> mockInputs = List.of("");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        String expectedMsg = "You need at least 2 players.";
        EasyMock.expect(assets.getString("error.minPlayers")).andReturn(
                expectedMsg
        );

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onError);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnError(onError);

        controller.onConfirmNames();

        EasyMock.verify(assets, view, onError);
    }

    @Test
    public void onConfirmNames_TwoPlayers_Success() {
        Runnable onSuccess = EasyMock.createMock(Runnable.class);

        List<String> mockInputs = List.of("Alice", "Bob");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        onSuccess.run();
        EasyMock.expectLastCall();

        EasyMock.replay(view, onSuccess);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
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
        Runnable onSuccess = EasyMock.createMock(Runnable.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        List<String> mockInputs = List.of("Alice", "Bob", "Dave");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        String expectedMsg = "Deck creation failed";
        onSuccess.run();
        EasyMock.expectLastCall().andThrow(new IllegalStateException(expectedMsg));

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(view, onSuccess, onError);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnSuccess(onSuccess);
        controller.setOnError(onError);

        controller.onConfirmNames();

        EasyMock.verify(view, onSuccess, onError);
    }

    @Test
    public void onRestartButton_buttonPressed_success() {
        Runnable onRestart = EasyMock.createMock(Runnable.class);

        onRestart.run();
        EasyMock.expectLastCall();

        EasyMock.replay(onRestart);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnRestart(onRestart);

        controller.onRestartButton();

        EasyMock.verify(onRestart);
    }

}