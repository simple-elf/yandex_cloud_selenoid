#!/bin/bash

MEMORY=$1
CORE=$1 # TODO second param???

source "/var/jenkins_home/.bashrc"

# shellcheck disable=SC2046
echo $(yc compute instance create-with-container \
--docker-compose-file docker-compose.yml \
--memory $MEMORY --cores $CORE --core-fraction 100 \
--zone ru-central1-c \
--network-interface subnet-id=b0c0itboa01ughk2ggl4 \
--format json)

#--public-ip \

#--service-account-name selenoid \