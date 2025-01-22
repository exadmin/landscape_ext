#!/bin/bash

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
if [ -x "$(command -v docker)" ]; then
    echo "Docker-compose is installed already."
else
    echo "Installing docker-compose..."
    sudo curl -L "https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  sudo chmod +x /usr/local/bin/docker-compose
fi

docker-compose --version

# Preparing folders
mkdir -p ./data/ssl