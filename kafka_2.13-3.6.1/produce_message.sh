#!/bin/bash

# Number of messages to produce
NUM_MESSAGES=10
echo "producing $NUM_MESSAGES :"

echo "{ \"firstName\": \"gil\", \"lastName\": \"test\", \"email\": \"email@test.com\", \"TEST\": \"1\" }"

echo "{ \"firstName\": \"gil\", \"lastName\": \"test\", \"email\": \"email@test.com\", \"TEST\": \"1\" }" | \
    bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092

for ((i = 1; i <= NUM_MESSAGES; i++)); do
    # Generate random data for firstName, lastName, and email
    firstName="test${i}"
    lastName="test${i}"
    email="a${i}@teste.com"
    echo "{ \"firstName\": \"$firstName\", \"lastName\": \"$lastName\", \"email\": \"$email\" }"
    # Produce message to Kafka
    echo "{ \"firstName\": \"$firstName\", \"lastName\": \"$lastName\", \"email\": \"$email\" }" | \
        bin/kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092

    # Optional: Add delay between producing messages
    # sleep 1
done


