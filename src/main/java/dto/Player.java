package dto;

/**
 * Represents a player participating in the message exchange.
 * <p>
 * Responsibilities:
 * - stores player identity;
 * - tracks sent and received message counters;
 * - creates messages according to the required message counter rule.
 */
public class Player {

    private final String name;
    private int sentCount;
    private int receivedCount;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSentCount() {
        return sentCount;
    }

    public int getReceivedCount() {
        return receivedCount;
    }

    /**
     * Sends a message by incrementing the sent counter and appending it to the provided message.
     *
     * @param message message text to use as a base
     * @return message with this player's sent counter appended
     */
    public String send(String message) {
        sentCount++;
        return message + "-" + sentCount;
    }

    /**
     * Registers receiving a message and increments the received counter.
     */
    public void receive() {
        receivedCount++;
    }

    /**
     * Receives a message and immdeiately creates a response.
     *
     * @param message received message
     * @return response message with this player's counter appended
     */
    public String receiveAndReply(String message) {
        receive();
        return send(message);
    }
}
