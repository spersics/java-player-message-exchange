import dto.Player;
import org.junit.jupiter.api.Test;
import service.single_process.SameProcessRunner;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SameProcessRunnerTest {

    @Test
    void correctRun() {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player initial = new Player("initiator");
        Player receiver = new Player("receiver");
        String message = "hello";

        int maxMessages = 4;

        sameProcessRunner.run(initial, receiver, message, maxMessages);
        assertEquals(maxMessages, initial.getSentCount());
        assertEquals(maxMessages, initial.getReceivedCount());
    }

    @Test
    void shouldThrowWhenFirstPlayerIsNull() {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player receiver = new Player("receiver");

        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(null, receiver, "hello", 10));
    }

    @Test
    void shouldThrowWhenSecondPlayerIsNull() {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player initial = new Player("initiator");

        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(initial, null, "hello", 10));
    }

    @Test
    void shouldThrowWhenMessageIsBlankOrNull() {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player initial = new Player("initiator");
        Player receiver = new Player("receiver");

        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(initial, receiver, "", 10));
        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(initial, receiver, null, 10));
    }

    @Test
    void shouldThrowWhenMaxMessagesIsNotPositiveOrZero() {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player initial = new Player("initiator");
        Player receiver = new Player("receiver");

        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(initial, receiver, "hello", -1));
        assertThrows(IllegalArgumentException.class, () -> sameProcessRunner.run(initial, receiver, "hello", 0));
    }
}
