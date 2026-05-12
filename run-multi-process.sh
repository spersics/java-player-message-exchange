#!/usr/bin/env bash
set -e

mvn clean test
mvn compile

PORT=5000
MAX_MESSAGES=10
MAIN_MESSAGE="hello"

java -cp target/classes service.multi_process.SocketReceiverMain "$PORT" &
RECEIVER_PID=$!

sleep 1

java -cp target/classes service.multi_process.SocketInitiatorMain localhost "$PORT" "$MAX_MESSAGES" "$INITIAL_MESSAGE"

wait "$RECEIVER_PID"

echo "Application finished."