package ui;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerCreateControllerTests {

    @Test
    public void onAddPlayer_CurrentZero_Success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);

        view.addPlayerField(1);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        PlayerCreateController controller = new PlayerCreateController(view);
        controller.onAddPlayer();

        assertEquals(1, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

    @Test
    public void onAddPlayer_CurrentThree_Success() {
        PlayerCreateView view = EasyMock.createMock(PlayerCreateView.class);

        PlayerCreateController controller = EasyMock.createMockBuilder(PlayerCreateController.class)
                .withConstructor(view)
                .createMock();

        view.addPlayerField(1);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(2);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(3);
        EasyMock.expectLastCall();
        view.setAddPlayerButtonDisabled(false);
        EasyMock.expectLastCall();

        view.addPlayerField(4);
        EasyMock.expectLastCall();

        view.setAddPlayerButtonDisabled(true);
        EasyMock.expectLastCall();

        EasyMock.replay(view);

        controller.onAddPlayer();
        controller.onAddPlayer();
        controller.onAddPlayer();

        controller.onAddPlayer();

        assertEquals(4, controller.getPlayerNumbers());

        EasyMock.verify(view);
    }

}