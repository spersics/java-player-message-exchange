package service.multi_process;

import dto.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Runs the initiator player in multi-process mode.
 * <p>
 * Responsibilities:
 * - connects to the receiver process over TCP;
 * - sends the initial message;
 * - continues the exchange until the initiator reaches the stop condition;
 * - sends a 'STOP' message to terminate the receiver gracefully.
 */
public class SocketInitiatorRunner {
    private static final String STOP_MESSAGE = "STOP";

    public void run(Player player, String host, int port, int maxMessages, String mainMessage) throws IOException {
        if (player == null || player.getName() == null) {
            throw new IllegalArgumentException("Player must be not null and their name must not be empty");
        }
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Host must be not null and contain necessary data");
        }
        if (port == 0 || port < 0) {
            throw new IllegalArgumentException("port must be positive and greater than 0");
        }
        if (mainMessage == null || mainMessage.isBlank()) {
            throw new IllegalArgumentException("MainMessage should be not null and contain at least 1 character");
        }
        if (maxMessages == 0 || maxMessages < 0) {
            throw new IllegalArgumentException("MaxMessages must be positive and more than zero");
        }

        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            String message = player.send(mainMessage);
            writer.println(message);
            System.out.println("Player --" + player.getName() + "-- sent message: " + message);

            while (player.getSentCount() < maxMessages || player.getReceivedCount() < maxMessages) {
                String response = reader.readLine();

                if (response == null) {
                    throw new IllegalStateException("Connection closed before stop condition reached");
                }
                System.out.println("Player --" + player.getName() + "-- received response: " + response);

                player.receive();
                if (player.getSentCount() == maxMessages && player.getReceivedCount() == maxMessages) {
                    break;
                }
                message = player.send(response);
                writer.println(message);
                System.out.println("Player --" + player.getName() + "-- sent message: " + message);
            }

            writer.println(STOP_MESSAGE);
            System.out.println("Sent STOP sign. Shutting down");
        }
    }
}
