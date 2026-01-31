#!/bin/bash

# Configuration
BOOTSTRAP_SERVER="localhost:9092"

echo "--- Initializing Kafka Server Topics ---"

# Create 'black' topic if it doesn't exist
./kafka-topics.sh --create --topic black --bootstrap-server $BOOTSTRAP_SERVER --partitions 3 --replication-factor 1 --if-not-exists

# Create 'color' topic if it doesn't exist
./kafka-topics.sh --create --topic color --bootstrap-server $BOOTSTRAP_SERVER --partitions 2 --replication-factor 1 --if-not-exists

echo "Topics 'black' and 'color' processed."
