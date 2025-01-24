#!/bin/bash

# Check this script is running under root
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

# Installing docker if required
if [ -x "$(command -v docker)" ]; then
    echo "Docker is installed already."
else
    echo "Installing docker..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sudo sh get-docker.sh
    sudo usermod -aG docker $USER
    # command
fi

docker --version

# Install Docker Compose if required
if [ -x "$(command -v docker-compose)" ]; then
    echo "Docker-compose is installed already."
else
    echo "Installing docker-compose..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

# Preparing folders for Let's Encrypt certificates
mkdir -p ./data/ssl
mkdir -p ./data/ls-data/logos
mkdir -p ./data/tmp

# Create symlinks to files & folders outside current folder - to enrich docker context
cp ./../landscape.yml ./data/tmp/landscape.yml
cp -rv --update=older ./../hosted_logos ./data/ls-data/logos
sudo docker compose --file ./compose.yml up