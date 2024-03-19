#!/bin/bash

# Kafka installation directory
KAFKA_HOME=./

# Kafka broker address
BOOTSTRAP_SERVERS=localhost:9092

# Topic name
TOPIC_NAME=my-topic

# Number of partitions
PARTITIONS=3

# Replication factor
REPLICATION_FACTOR=1

# Path to Kafka topics script
KAFKA_TOPICS_SCRIPT=$KAFKA_HOME/bin/kafka-topics.sh

# Create Kafka topic
$KAFKA_TOPICS_SCRIPT --create \
    --bootstrap-server $BOOTSTRAP_SERVERS \
    --topic $TOPIC_NAME \
    --partitions $PARTITIONS \
    --replication-factor $REPLICATION_FACTOR
