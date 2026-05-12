import dto.Player;
import org.junit.jupiter.api.Test;
import service.multi_process.SocketInitiatorRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SocketInitiatorRunnerTest {
    @Test
    void shouldThrowWhenPlayerIsNull() {
        SocketInitiatorRunner socketInitiatorRunner = new SocketInitiatorRunner();
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(null, "localhost", 5000, 10, "hello"));
    }

    @Test
    void shouldThrowWhenHostIsNullOrBlank() {
        SocketInitiatorRunner socketInitiatorRunner = new SocketInitiatorRunner();
        Player player = new Player("initiator");
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, null, 5000, 10, "hello"));
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "", 5000, 10, "hello"));
    }

    @Test
    void shouldThrowWhenPortIsZeroOrNegative() {
        SocketInitiatorRunner socketInitiatorRunner = new SocketInitiatorRunner();
        Player player = new Player("initiator");
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", 0, 10, "hello"));
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", -5000, 10, "hello"));
    }

    @Test
    void shouldThrowWhenMaxMessagesIsNotPositiveOrZero() {
        SocketInitiatorRunner socketInitiatorRunner = new SocketInitiatorRunner();
        Player player = new Player("initiator");
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", 5000, 0, "hello"));
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", 5000, -10, "hello"));
    }

    @Test
    void shouldThrowWhenMessageIsBlankOrNull() {
        SocketInitiatorRunner socketInitiatorRunner = new SocketInitiatorRunner();
        Player player = new Player("initiator");
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", 5000, 10, null));
        assertThrows(IllegalArgumentException.class, () -> socketInitiatorRunner.run(player, "localhost", 5000, 10, ""));
    }
}
