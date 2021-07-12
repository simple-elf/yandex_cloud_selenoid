#!/bin/bash

MEMORY=$1
CORE=$1 # TODO second param???

source "/var/jenkins_home/.bashrc"

# shellcheck disable=SC2046
echo $(yc compute instance create-with-container \
--docker-compose-file docker-compose.yml \
--memory $MEMORY --cores $CORE --core-fraction 100 \
--format json)

#--public-ip \
#--zone ru-central1-c \
#--service-account-name selenoid \