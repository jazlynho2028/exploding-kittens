package ui;

import io.cucumber.java.an.E;
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
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        view.addPlayerField(PLAYER_COUNT_ONE);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_ONE, controller.getPlayerNumbers());

        EasyMock.verify(assets, view);
    }

    @Test
    public void onAddPlayer_CurrentThree_Success() {
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);

        PlayerCreateController controller = EasyMock.createMockBuilder(PlayerCreateController.class)
                .withConstructor(assets, view)
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

        EasyMock.replay(assets, view);

        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(assets, view);
    }

    @Test
    public void onAddPlayer_CurrentFour_Failed() {
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        PlayerCreateController controller = EasyMock.createMockBuilder(PlayerCreateController.class)
                .withConstructor(assets, view)
                .createMock();

        controller.setOnError(onError);

        for (int i = PLAYER_COUNT_ONE; i <= PLAYER_COUNT_FOUR; i++) {
            view.addPlayerField(i);
            EasyMock.expectLastCall();
            view.setAddPlayerButtonDisabled(i == PLAYER_COUNT_FOUR);
            EasyMock.expectLastCall();
        }

        String expectedMsg = "You cannot have more than 4 players.";
        EasyMock.expect(assets.getString("error.maxPlayers")).andReturn(
                expectedMsg
        );

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onError);

        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        controller.onAddPlayer();

        assertEquals(PLAYER_COUNT_FOUR, controller.getPlayerNumbers());

        EasyMock.verify(assets, view, onError);
    }

    @Test
    public void onConfirmNames_OnePlayer_Failed() {
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);

        List<String> mockInputs = List.of("Alice");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);


        String expectedMsg = "You need at least 2 players.";
        EasyMock.expect(assets.getString("error.minPlayers")).andReturn(
                expectedMsg
        );

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onError, onSuccess);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnError(onError);
        controller.setOnSuccess(onSuccess);

        controller.onConfirmNames();

        EasyMock.verify(assets, view, onError, onSuccess);
    }

    @Test
    public void onConfirmNames_TwoPlayers_Success() {
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);

        List<String> mockInputs = List.of("Alice", "Bob");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        onSuccess.run();
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onSuccess);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnSuccess(onSuccess);

        controller.onConfirmNames();

        EasyMock.verify(assets, view, onSuccess);

        List<String> confirmed = controller.getConfirmedNames();
        assertEquals(PLAYER_COUNT_TWO, confirmed.size());
        assertEquals("Alice", confirmed.get(0));
        assertEquals("Bob", confirmed.get(1));
    }

    @Test
    public void onConfirmNames_onSuccess_Error() {
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        Runnable onSuccess = EasyMock.createMock(Runnable.class);
        Consumer<String> onError = EasyMock.createMock(Consumer.class);

        List<String> mockInputs = List.of("Alice", "Bob", "Dave");
        EasyMock.expect(view.getPlayerNamesFromFields()).andReturn(mockInputs);

        String expectedMsg = "Deck creation failed";
        onSuccess.run();
        EasyMock.expectLastCall().andThrow(new IllegalStateException(expectedMsg));

        onError.accept(expectedMsg);
        EasyMock.expectLastCall();

        EasyMock.replay(assets, view, onSuccess, onError);

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.setOnSuccess(onSuccess);
        controller.setOnError(onError);

        controller.onConfirmNames();

        EasyMock.verify(assets, view, onSuccess, onError);
    }

    @Test
    public void getPlayerNumbers_empty_return0(){
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        int expected = 0;

        PlayerCreateController controller = new PlayerCreateController(assets, view);

        int actual = controller.getPlayerNumbers();

        assertEquals(expected, actual);
    }

    @Test
    public void getPlayerNumbers_onePlayer_return1(){
        AssetProvider assets = EasyMock.createMock(AssetProvider.class);
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);
        int expected = 1;

        PlayerCreateController controller = new PlayerCreateController(assets, view);
        controller.onAddPlayer();

        int actual = controller.getPlayerNumbers();

        assertEquals(expected, actual);
    }
}