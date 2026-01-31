#!/bin/bash

# Configuration
BOOTSTRAP_SERVER="localhost:9092"

echo "--- Checking Kafka Server Topics ---"

TOPICS=$(./kafka-topics.sh --list --bootstrap-server $BOOTSTRAP_SERVER)

if echo "$TOPICS" | grep -q "^black$"; then
    echo "Topic 'black' exists: true"
else
    echo "Topic 'black' exists: false"
fi

if echo "$TOPICS" | grep -q "^color$"; then
    echo "Topic 'color' exists: true"
else
    echo "Topic 'color' exists: false"
fi

if echo "$TOPICS" | grep -q "^black$" && echo "$TOPICS" | grep -q "^color$"; then
    echo "All required topics are present."
else
    echo "One or more topics are missing."
fi
