#!/bin/bash

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


rsa_key_size=4096



if [ ! -e "$DATA_PATH/certbot/conf/options-ssl-nginx.conf" ] || [ ! -e "$DATA_PATH/certbot/conf/ssl-dhparams.pem" ]; then
  echo "### Downloading recommended TLS parameters ..."
  mkdir -p "$DATA_PATH/certbot/conf"
  curl -s https://raw.githubusercontent.com/certbot/certbot/master/certbot-nginx/certbot_nginx/_internal/tls_configs/options-ssl-nginx.conf > "$DATA_PATH/certbot/conf/options-ssl-nginx.conf"
  curl -s https://raw.githubusercontent.com/certbot/certbot/master/certbot/certbot/ssl-dhparams.pem > "$DATA_PATH/certbot/conf/ssl-dhparams.pem"
  echo
fi

docker compose up -d