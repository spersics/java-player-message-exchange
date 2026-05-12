package service.multi_process;

import dto.Player;

/**
 * Entry point for the initiator player in the multi-process mode.
 * <p>
 * Responsibilities:
 * - starts a separate JVM process for the initiator player;
 * - creates initiator Player object;
 * - reads host, port, max message count, and initial message from command-line arguments;
 * - delegates socket communication and stop condition handling to SocketInitiatorRunner;
 * - prints the current process PID to demonstrate separate process execution.
 */
public class SocketInitiatorMain {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;
        int maxMessages = args.length > 2 ? Integer.parseInt(args[2]) : 10;
        String mainMessage = args.length > 3 ? args[3] : "hello";

        System.out.println("Socket initiator started. PID=" + ProcessHandle.current().pid());

        Player receiver = new Player("initiator");
        SocketInitiatorRunner initiatorRunner = new SocketInitiatorRunner();
        initiatorRunner.run(receiver, host, port, maxMessages, mainMessage);
    }
}
