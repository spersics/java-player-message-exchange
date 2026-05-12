package service.single_process;

import dto.Player;

/**
 * Runs the message exchange between two Player objects in the same JVM process.
 * <p>
 * Responsibilities:
 * - validates input parameters;
 * - coordinates the message exchange between initiator and receiver;
 * - stop when the initiator has send and received configured number of messages.
 */
public class SameProcessRunner {

    public void run(Player first, Player second, String mainMessage, int maxMessages) {
        if (first == null || first.getName() == null || second == null || second.getName() == null) {
            throw new IllegalArgumentException("Players must be not null and their names must not be empty");
        }
        if (mainMessage == null || mainMessage.isBlank()) {
            throw new IllegalArgumentException("MainMessage should be not null and contain at least 1 character");
        }
        if (maxMessages == 0 || maxMessages < 0) {
            throw new IllegalArgumentException("MaxMessages must be positive and more than zero");
        }

        String message = first.send(mainMessage);
        System.out.println("Player --" + first.getName() + "-- sent initial message: " + message);

        while (first.getSentCount() < maxMessages || first.getReceivedCount() < maxMessages) {
            String response = second.receiveAndReply(message);
            System.out.println("Player --" + second.getName() + "-- sent response message: " + response);
            first.receive();
            if (first.getSentCount() == maxMessages && first.getReceivedCount() == maxMessages) {
                break;
            }
            message = first.send(response);
            System.out.println("Player --" + first.getName() + "-- sent response message: " + message);
        }
    }
}
