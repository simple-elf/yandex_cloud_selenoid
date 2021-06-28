#!/bin/bash

INSTANCE_ID=$1
echo "INSTANCE_ID: $INSTANCE_ID"

source "/var/jenkins_home/.bashrc"
yc --version
yc compute instance delete "$INSTANCE_ID"