#!/bin/bash

sudo apt-get install jq -y
curl https://storage.yandexcloud.net/yandexcloud-yc/install.sh | bash
source "/var/jenkins_home/.bashrc"
echo $PATH

#yc --help
echo "YC_CLOUD_ID: $YC_CLOUD_ID"

yc config set token $YC_TOKEN
yc config set folder-id $YC_FOLDER_ID
yc config set cloud-id $YC_CLOUD_ID