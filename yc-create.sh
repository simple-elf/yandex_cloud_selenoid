#!/bin/bash

MEMORY=$1
CORE=$1 # TODO second param???

source "/var/jenkins_home/.bashrc"
yc --version

INSTANCE_ID=$(yc compute instance create-with-container \
--docker-compose-file docker-compose.yml \
--zone ru-central1-c \
--public-ip \
--memory $MEMORY --cores $CORE --core-fraction 100 \
--service-account-name selenoid \
--format json | jq .id -r)

export INSTANCE_ID=$INSTANCE_ID
echo "$INSTANCE_ID"