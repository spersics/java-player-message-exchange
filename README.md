## Task Summary

The project implements a small pure Java message exchange application.

The goal is to model two players that communicate with each other:

- one player acts as the initiator and sends the first message;
- every received message is followed by a response message with the sender's counter appended;
- the exchange stops gracefully after the initiator has sent and received the configured number of messages;
- the same logic is demonstrated in two modes:
    - both players running in the same JVM process;
    - each player running in a separate JVM process and communicating over TCP;
- the focus is on clean design, simple technology, and clear class responsibilities.

---

## Overview

This project implements a simple message exchange between two 'Player' objects.

The task supports two execution modes:

1. Same-process mode - both players run in the same JVM process.
2. Multi-process mode - each player runs in a separate JVM process and communicates over TCP sockets.


The implementation uses pure Java for the application logic. Maven is used only for building and running tests.

---

## Requirements Covered

- Two players are created.
- The initiator send the first message.
- Each player responds by appending its own sent-message counter.
- The program stops gracefully after the initiator has sent 10 messages and received 10 messages back.
- Same-process mode runs both players in one JVM.
- Multi-process mode runs players in separate JVM process with different PIDs.
- Each class has documented responsibilities.
- Shell scripts are provided to run the program.

---

## Assumptions

- The initial message sent by the initiator is counted as the first message sent by the initiator.
- In multi-process mode TCP sockets are used as the simplest communication mechanism between two JVM processes.
- After the initiator reaches the stop condition in multi-process mode, it sends a 'STOP' message to the receiver to process graceful shutdown.
- Console output is done with 'System.out.println' to keep the solution simple and avoid additional logging dependencies.

---

## Project Structure

```text
src/main/java
├── dto
│   └── Player.java
├── service
│   ├── single_process
│   │   ├── SameProcessMain.java
│   │   └── SameProcessRunner.java
│   └── multi_process
│       ├── SocketInitiatorMain.java
│       ├── SocketInitiatorRunner.java
│       ├── SocketReceiverMain.java
│       └── SocketReceiverRunner.java
 
 
src/test/java
├── PlayerLogicTest.java
├── SameProcessRunnerTest.java
├── SocketInitiatorRunnerTest.java
└── SocketReceiverRunnerTest.java
```

---

## Class Responsibilities

### Player

Represents a player participating in the message exchange.

Responsibilities:

- stores player identity;
- tracks sent and received message counters;
- create messages according to the required message counter rule.

### SameProcessMain

Entry point for same-process mode.

Responsibilities:

- creates two Player objects in the same JVM;
- starts SameProcessRunner with default parameters;
- demonstrates the required message exchange in a single Java process.

### SameProcessRunner

Runs the message exchange between two players in the same JVM process.

Responsibilities:

- validates input parameters;
- coordinates the message exchange between initiator and receiver;
- stops when the initiator has sent and received configured number of messages.

### SocketReceiverMain

Entry point for the receiver player in multi-process mode.

Responsibilities:

- starts a separate JVM process for the receiver player;
- creates receiver Player object;
- reads the TCP port from command-line arguments or uses the default port;
- delegates socket communication to SocketReceiverRunner;
- prints the current process PID to demonstrate separate process execution.

### SocketReceiverRunner

Runs the receiver side in multi-process mode.

Responsibilities:

- opens a TCP server socket;
- accepts connection from the initiator;
- receive messages and sends responses using Player logic;
- stops gracefully when the 'STOP' message is received.

### SocketInitiatorMain

Entry point for the initiator player in multi-process mode.

Responsibilities:

- starts a separate JVM process for the initiator player;
- creates initiator Player object;
- reads host, port, max message count, and initial message from command-line arguments;
- delegates socket communication and stop condition handling to SocketInitiatorRunner;
- prints the current process PID to demonstrate separate process execution.


### SocketInitiatorRunner

Runs the initiator side in multi-process mode.

Responsibilities:

- connects to the receiver process over TCP;
- sends the initial message;
- continues the exchange until the initiator reaches the stop condition;
- sends a 'STOP' message to terminate the receiver gracefully.

---

## Dependencies

The application logic uses pure Java.

The pom.xml contains dependencies only for tests:

- JUnit 5
- AssertJ

No Spring, Lombok, logging framework, or other application framework is used.

---

## Tests

Automated tests cover:

- Player message/counter logic;
- same-process message exchange;
- validation for invalid input for same-process mode;
- validation for invalid input for multi-process runner classes.

Multi-process mode is verified by run-multi-process.sh, because the requirement is to start separate JVM processes with different PIDs.

Automated unit tests do not start additional JVM processes in order to keep the test suite simple and deterministic.

---

## Build and Run

### Requirements

- Java 17+
- Maven
- Bash-compatible shell

---

## Shell Scripts

The project provides two Bash scripts in the project root:

- `run-same-process.sh` — runs the same-process mode, where both players are executed in one JVM process.
- `run-multi-process.sh` — runs the multi-process mode, where receiver and initiator are started as separate JVM processes and communicate over TCP.

Both scripts run tests before starting the application. If tests fail, the application is not started.

### Run scripts from Bash

On Linux/macOS/Git Bash:

```
bash run-same-process.sh

bash run-multi-process.sh
```
---

### Optional: make scripts executable

```
chmod +x run-same-process.sh

chmod +x run-multi-process.sh
```

Then run:
```
./run-same-process.sh

./run-multi-process.sh
```


