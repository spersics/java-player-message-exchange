package service.multi_process;

import dto.Player;

/**
 * Entry point for the receiver player in multi-process mode.
 * <p>
 * Responsibilities:
 * - starts a separate JVM process for the receiver player;
 * - creates receiver Player object;
 * - reads the TCP port from command-line arguments or uses the default port;
 * - delegates socket communication to SocketReceiverRunner;
 * - prints the current process PID to demonstrate separate process execution.
 */
public class SocketReceiverMain {
    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 5000;

        System.out.println("Socket receiver started. PID=" + ProcessHandle.current().pid());

        Player receiver = new Player("receiver");
        SocketReceiverRunner receiverRunner = new SocketReceiverRunner();
        receiverRunner.run(receiver, port);
    }
}
