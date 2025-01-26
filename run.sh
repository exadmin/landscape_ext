#!/bin/bash

# Common algo
# 1. load env variables & ensure they have correct values
# 2. change default values in $DATA_PATH/nginx/default.conf
# 3. copy default.conf file into NGINX container during startup
# 4. copy $DATA_PATH/certbot/conf/options-ssl-nginx.conf into CERTBOT container
# 5. copy $DATA_PATH/certbot/conf/ssl-dhparams.pem into CERTBOT container
# 6. start CERTBOT container in renew-if-needed mode
# 7. start NGINX container
# 8. start MYLANDSCAPE container


# Check this script is running under root
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

# Load predefined settings from external file
source .env

# Remove carriage return and new line chars if exists
DATA_PATH=${DATA_PATH//[$'\n\r']/}
SRV_CN=${SRV_CN//[$'\n\r']/}
USER_EMAIL=${USER_EMAIL//[$'\n\r']/}
# todo: check required parameters are not empty and not default

# Ensure docker compose is installed
if ! [ -x "$(command -v docker-compose)" ]; then
  echo 'Error: docker-compose is not installed.' >&2
  exit 1
fi

# Prepare nginx configuration
echo "Prepare nginx configuration"
echo "Ensure nginx configuration is correct at $DATA_PATH/nginx"
mkdir -p $DATA_PATH/nginx
[ -e $DATA_PATH/nginx/default.conf ] && rm $DATA_PATH/nginx/default.conf
cp ./addons/nginx/default.conf $DATA_PATH/nginx/default.conf
sed -i "s/example.org/$SRV_CN/g" $DATA_PATH/nginx/default.conf

docker compose up -d