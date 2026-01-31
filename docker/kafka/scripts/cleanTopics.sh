#!/bin/bash

# Configuration
BOOTSTRAP_SERVER="localhost:9092"

echo "--- Cleaning Kafka Server Topics ---"

# Delete 'black' topic
./kafka-topics.sh --delete --topic black --bootstrap-server $BOOTSTRAP_SERVER --if-exists

# Delete 'color' topic
./kafka-topics.sh --delete --topic color --bootstrap-server $BOOTSTRAP_SERVER --if-exists

echo "Topics 'black' and 'color' deleted (if they existed)."
