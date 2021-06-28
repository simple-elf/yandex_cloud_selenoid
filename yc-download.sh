#!/bin/bash

curl https://storage.yandexcloud.net/yandexcloud-yc/install.sh | bash
source "/var/jenkins_home/.bashrc"
echo $PATH
export PATH="/home/yandex-cloud/bin:${PATH}"
echo $PATH

yc --help
echo "YC_CLOUD_ID: $YC_CLOUD_ID"

yc init --cloud-id $YC_CLOUD_ID --folder-id $YC_FOLDER_ID --token $YC_TOKEN
#yc config set token $YC_TOKEN
#yc config set folder-id $YC_FOLDER_ID
#yc config set cloud-id $YC_CLOUD_ID