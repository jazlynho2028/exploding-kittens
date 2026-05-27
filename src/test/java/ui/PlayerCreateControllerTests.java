package ui;

import domain.GameConstants;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCreateControllerTests {

    private static final int PLAYER_COUNT_ONE = 1;
    private static final int PLAYER_COUNT_TWO = 2;
    private static final int PLAYER_COUNT_THREE = 3;
    private static final int PLAYER_COUNT_FOUR = 4;

    private PlayerCreateView view;

//    @BeforeEach
//    public void setUpExpectations() {
//        view = EasyMock.createMock(PlayerCreateView.class);
//
//        view.bindUI(
//                EasyMock.anyObject(Runnable.class),
//                EasyMock.anyObject(Runnable.class),
//                EasyMock.anyObject(Runnable.class)
//        );
//        EasyMock.expectLastCall();
//    }
//
    @Test
    public void buildPlayerCreateScene_called_success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Scene expectedScene = EasyMock.createMock(Scene.class);
        PlayerCreateController controller = EasyMock.createMockBuilder(
                PlayerCreateController.class
                )
                .withConstructor(view)
                .addMockedMethod("buildDependentUI")
                .addMockedMethod("bindUI")
                .createMock();

        controller.buildDependentUI();
        EasyMock.expectLastCall();

        controller.bindUI();
        EasyMock.expectLastCall();

        EasyMock.expect(view.createPlayerCreateScene()).andReturn(expectedScene);

        EasyMock.replay(view, controller);

        Scene actualScene = controller.buildPlayerCreateScene();
        assertSame(expectedScene, actualScene);

        EasyMock.verify(view, controller);
    }

    @Test
    public void buildDependentUI_called_success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        PlayerCreateController controller = EasyMock.createMockBuilder(
                        PlayerCreateController.class
                )
                .withConstructor(view)
                .addMockedMethod("onAddPlayer")
                .createMock();

        controller.onAddPlayer();
        EasyMock.expectLastCall().times(GameConstants.MIN_PLAYERS);

        EasyMock.replay(controller);

        controller.buildDependentUI();

        EasyMock.verify(controller);
    }

    @Test
    public void bindUI_called_success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Runnable onRestart = EasyMock.createMock(Runnable.class);

        view.bindAddPlayerButton(EasyMock.anyObject());
        EasyMock.expectLastCall();

        view.bindConfirmButton(EasyMock.anyObject());
        EasyMock.expectLastCall();

        view.bindRestartButton(onRestart);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.setOnRestart(onRestart);

        controller.bindUI();

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_currentZero_success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        int expectedCount = PLAYER_COUNT_ONE;

        view.addPlayerField(expectedCount);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(false);

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);

        controller.onAddPlayer();

        int actualCount = controller.getPlayerFieldsCount();
        assertEquals(expectedCount, actualCount);

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_currentOne_success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        int expectedCount = PLAYER_COUNT_TWO;

        for (int i = 0; i < expectedCount; i++) {
            view.addPlayerField(i + 1);
            EasyMock.expectLastCall();
        }

        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall().times(expectedCount);

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);

        for (int i = 0; i < expectedCount; i++) {
            controller.onAddPlayer();
        }

        int actualCount = controller.getPlayerFieldsCount();
        assertEquals(expectedCount, actualCount);

        EasyMock.verify(view);
    }

//    @Test
//    public void onAddPlayer_CurrentTwo_Success() {
//        view.addPlayerField(PLAYER_COUNT_THREE);
//        EasyMock.expectLastCall();
//        view.setAddPlayerButtonDisabled(false);
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.onAddPlayer();
//
//        assertEquals(PLAYER_COUNT_THREE, controller.getPlayerFieldsCount());
//
//        EasyMock.verify(view);
//    }
//
//    @Test
//    public void onAddPlayer_CurrentThree_Success() {
//        view.addPlayerField(PLAYER_COUNT_THREE);
//        EasyMock.expectLastCall();
//        view.setAddPlayerButtonDisabled(false);
//        EasyMock.expectLastCall();
//
//        view.addPlayerField(PLAYER_COUNT_FOUR);
//        EasyMock.expectLastCall();
//        view.setAddPlayerButtonDisabled(true);
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.onAddPlayer();
//        controller.onAddPlayer();
//
//        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerFieldsCount());
//
//        EasyMock.verify(view);
//    }
//
//    @Test
//    public void onAddPlayer_CurrentFour_Failed() {
//        Consumer<String> onError = EasyMock.createMock(Consumer.class);
//
//        view.addPlayerField(PLAYER_COUNT_THREE);
//        EasyMock.expectLastCall();
//        view.setAddPlayerButtonDisabled(false);
//        EasyMock.expectLastCall();
//
//        view.addPlayerField(PLAYER_COUNT_FOUR);
//        EasyMock.expectLastCall();
//        view.setAddPlayerButtonDisabled(true);
//        EasyMock.expectLastCall();
//
//        String expectedMsg = "error.maxPlayers";
//
//        onError.accept(expectedMsg);
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view, onError);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.setOnError(onError);
//        controller.onAddPlayer();
//        controller.onAddPlayer();
//        controller.onAddPlayer();
//
//        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerFieldsCount());
//
//        EasyMock.verify(view, onError);
//    }
//
//    @Test
//    public void onConfirmNames_OnePlayer_Failed() {
//        Consumer<String> onError = EasyMock.createMock(Consumer.class);
//
//        List<String> mockInputs = List.of("");
//        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);
//
//        String expectedMsg = "error.minPlayers";
//
//        onError.accept(expectedMsg);
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view, onError);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.setOnError(onError);
//
//        controller.onConfirmNames();
//
//        EasyMock.verify(view, onError);
//    }
//
//    @Test
//    public void onConfirmNames_TwoPlayers_Success() {
//        Runnable onSuccess = EasyMock.createMock(Runnable.class);
//
//        List<String> mockInputs = List.of("Alice", "Bob");
//        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);
//
//        onSuccess.run();
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view, onSuccess);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.setOnSuccess(onSuccess);
//
//        controller.onConfirmNames();
//
//        EasyMock.verify(view, onSuccess);
//
//        List<String> confirmed = controller.getConfirmedNames();
//        assertEquals(PLAYER_COUNT_TWO, confirmed.size());
//        assertEquals("Alice", confirmed.get(0));
//        assertEquals("Bob", confirmed.get(1));
//    }
//
//    @Test
//    public void onConfirmNames_onSuccess_Error() {
//        Runnable onSuccess = EasyMock.createMock(Runnable.class);
//        Consumer<String> onError = EasyMock.createMock(Consumer.class);
//
//        List<String> mockInputs = List.of("Alice", "Bob", "Dave");
//        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);
//
//        String expectedMsg = "Deck creation failed";
//        onSuccess.run();
//        EasyMock.expectLastCall().andThrow(new IllegalStateException(expectedMsg));
//
//        onError.accept(expectedMsg);
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(view, onSuccess, onError);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.setOnSuccess(onSuccess);
//        controller.setOnError(onError);
//
//        controller.onConfirmNames();
//
//        EasyMock.verify(view, onSuccess, onError);
//    }
//
//    @Test
//    public void onRestartButton_buttonPressed_success() {
//        Runnable onRestart = EasyMock.createMock(Runnable.class);
//
//        onRestart.run();
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(onRestart);
//
//        PlayerCreateController controller = new PlayerCreateController(view);
//        controller.setOnRestart(onRestart);
//
//        controller.onRestartButton();
//
//        EasyMock.verify(onRestart);
//    }

}