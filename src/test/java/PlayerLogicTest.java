import dto.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerLogicTest {
    @Test
    void shouldSendMessageWithCounter() {
        Player player = new Player("initiator");

        String message = player.send("hello");

        assertEquals("hello-1", message);
        assertEquals(1, player.getSentCount());
    }

    @Test
    void shouldReceiveAndReplyWithCounter() {
        Player player = new Player("receiver");

        String response = player.receiveAndReply("hello-1");

        assertEquals("hello-1-1", response);
        assertEquals(1, player.getReceivedCount());
        assertEquals(1, player.getSentCount());
    }
}
