#!/usr/bin/env bash
set -e

mvn clean test
mvn compile

java -cp target/classes service.single_process.SameProcessMain

echo "Application finished."