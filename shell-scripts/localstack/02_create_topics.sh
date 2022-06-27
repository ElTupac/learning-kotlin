#!/usr/bin/env bash

function createTopic() {
    printf "Creating topic: %s\n" $1
    awslocal sns create-topic --name $1
}

createTopic sample-topic-dev
# ... add more queue names here

printf '\n\nListing all topics created...'

awslocal sns list-topics
