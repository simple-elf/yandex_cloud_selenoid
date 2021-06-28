#!/bin/bash

MEMORY=$1
CORE=$1 # TODO second param???

ID=yc compute instance create-with-container \
--docker-compose-file docker-compose.yml \
--zone ru-central1-c \
--public-ip \
--memory 4 --cores 4 --core-fraction 100 \
--service-account-name selenoid \
--format json | jq .id -r

echo "ID: $ID"
export $ID