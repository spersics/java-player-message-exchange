package service.multi_process;

import dto.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Runs the receiver players in multi-process mode.
 * <p>
 * Responsibilities:
 * - opens a TCP server socket;
 * - accepts connection from the initiator;
 * - receives messages and sends responses using Player logic;
 * - stops gracefully when the 'STOP' message is received.
 */
public class SocketReceiverRunner {
    private static final String STOP_MESSAGE = "STOP";

    public void run(Player player, int port) throws IOException {
        if (player == null || player.getName() == null) {
            throw new IllegalArgumentException("Player must be not null and their name must not be empty");
        }
        if (port == 0 || port < 0) {
            throw new IllegalArgumentException("port must be positive and greater than 0");
        }

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Receiver is waiting on port " + port);

            try (Socket socket = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                String message;

                while ((message = reader.readLine()) != null) {
                    System.out.println("Player --" + player.getName() + "-- received response: " + message);
                    if (STOP_MESSAGE.equals(message)) {
                        System.out.println("Received STOP. Shutting down");
                        break;
                    }

                    String response = player.receiveAndReply(message);
                    writer.println(response);
                    System.out.println("Player --" + player.getName() + "-- sent message: " + response);
                }
            }
        }
    }
}
