package service.single_process;

import dto.Player;

/**
 * Entry point for running same-process mode.
 * <p>
 * Responsibilities:
 * - creates two Player objects in the same JVM;
 * - starts SameProcessRunner with default parameters;
 * - demonstrates the required message exchange in a single Java process.
 */
public class SameProcessMain {
    public static void main(String[] args) {
        SameProcessRunner sameProcessRunner = new SameProcessRunner();

        Player first = new Player("initiator");
        Player second = new Player("receiver");

        sameProcessRunner.run(first, second, "hello", 10);
    }
}
