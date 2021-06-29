#!/bin/bash

source "/var/jenkins_home/.bashrc"

# shellcheck disable=SC2046
echo $(yc compute instance get $1 --format json)