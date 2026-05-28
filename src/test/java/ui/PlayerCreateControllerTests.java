package ui;

import domain.GameConstants;
import javafx.scene.Scene;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCreateControllerTests {

    private PlayerCreateView view;

    @BeforeEach
    public void setUp() {
        view = EasyMock.createMock(PlayerCreateView.class);
    }

    @Test
    public void buildPlayerCreateScene_called_success() {
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

    @ParameterizedTest
    @CsvSource({
            "1",
            "2",
            "3",
            "4"
    })
    public void onAddPlayer_add1To4_success(int expectedCount) {
        for (int i = 0; i < expectedCount; i++) {
            view.addPlayerField(i + 1);
            EasyMock.expectLastCall();
        }

        if (expectedCount == GameConstants.MAX_PLAYERS) {
            view.setAddPlayerButtonDisabled(true);
            EasyMock.expectLastCall();
        }

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);

        for (int i = 0; i < expectedCount; i++) {
            controller.onAddPlayer();
        }

        int actualCount = controller.getPlayerFieldsCount();
        assertEquals(expectedCount, actualCount);

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_currentFour_noChange() {
        int expectedCount = GameConstants.MAX_PLAYERS;

        for (int i = 0; i < expectedCount; i++) {
            view.addPlayerField(i + 1);
            EasyMock.expectLastCall();
        }

        view.setAddPlayerButtonDisabled(true);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);

        for (int i = 0; i < expectedCount + 1; i++) {
            controller.onAddPlayer();
        }

        int actualCount = controller.getPlayerFieldsCount();
        assertEquals(expectedCount, actualCount);

        EasyMock.verify(view);
    }

    @Test
    public void onConfirmNames_called_success() {
        Runnable onSuccess = EasyMock.createMock(Runnable.class);
        PlayerCreateController controller = EasyMock.createMockBuilder(
                PlayerCreateController.class
                )
                .withConstructor(view)
                .addMockedMethod("populateConfirmedNames")
                .createMock();

        controller.populateConfirmedNames();
        EasyMock.expectLastCall();

        onSuccess.run();
        EasyMock.expectLastCall();

        EasyMock.replay(onSuccess, controller);

        controller.setOnSuccess(onSuccess);
        controller.onConfirmNames();

        EasyMock.verify(onSuccess, controller);
    }

    @Test
    public void onConfirmNames_called_failed() {
        Runnable onSuccess = EasyMock.createMock(Runnable.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);
        String expectedMsg = "An error occurred.";
        PlayerCreateController controller = EasyMock.createMockBuilder(
                        PlayerCreateController.class
                )
                .withConstructor(view)
                .addMockedMethod("populateConfirmedNames")
                .createMock();

        controller.populateConfirmedNames();
        EasyMock.expectLastCall();

        onSuccess.run();
        EasyMock.expectLastCall().andThrow(new RuntimeException(expectedMsg));

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(onSuccess, onError, controller);

        controller.setOnError(onError);
        controller.setOnSuccess(onSuccess);
        controller.onConfirmNames();

        EasyMock.verify(onSuccess, onError, controller);
    }

    private static Stream<Arguments> confirmedNamesArguments() {
        return Stream.of(
                Arguments.of(List.of(), List.of()),
                Arguments.of(List.of("Steve"), List.of("Steve")),
                Arguments.of(List.of("Steve ", "Steve"), List.of("Steve", "Steve")),
                Arguments.of(List.of(" Steve ", " Steve "), List.of("Steve", "Steve")),
                Arguments.of(List.of("", " Steve"), List.of("Steve"))
        );
    }

    @ParameterizedTest
    @MethodSource("confirmedNamesArguments")
    public void populateConfirmedNames_givenInputsFromView_populatedAndTrimmed(
            List<String> inputsFromView,
            List<String> expectedNames
    ) {
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(inputsFromView);

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.populateConfirmedNames();

        List<String> actualNames = controller.getConfirmedNames();
        assertEquals(expectedNames, actualNames);

        EasyMock.verify(view);
    }

}