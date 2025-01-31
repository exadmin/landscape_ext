#!/bin/bash

# Check this script is running under root
if [ "$EUID" -ne 0 ]
  then echo "Please run as root"
  exit
fi

# Ensure docker compose is installed
if ! [ -x "$(command -v docker-compose)" ]; then
  echo 'Error: docker-compose is not installed.' >&2
  exit 1
fi

# Run docker compose configuration

## stop all containers
sudo docker stop $(sudo docker ps -a -q)
sudo docker system prune --all --force

## restart build
sudo docker compose -f ./compose.yml up -d --force-recreate