import dto.Player;
import org.junit.jupiter.api.Test;
import service.multi_process.SocketReceiverRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SocketReceiverRunnerTest {
    @Test
    void shouldThrowWhenPlayerIsNull() {
        SocketReceiverRunner socketReceiverRunner = new SocketReceiverRunner();
        assertThrows(IllegalArgumentException.class, () -> socketReceiverRunner.run(null, 5000));
    }

    @Test
    void shouldThrowWhenPortIsZeroOrNegative() {
        SocketReceiverRunner socketReceiverRunner = new SocketReceiverRunner();
        Player player = new Player("receiver");
        assertThrows(IllegalArgumentException.class, () -> socketReceiverRunner.run(player, 0));
        assertThrows(IllegalArgumentException.class, () -> socketReceiverRunner.run(player, -5000));
    }
}
