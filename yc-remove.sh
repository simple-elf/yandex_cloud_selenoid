#!/bin/bash

INSTANCE_ID=$1
echo "INSTANCE_ID: $INSTANCE_ID"

yc compute instance delete "$INSTANCE_ID"