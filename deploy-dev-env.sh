#!/bin/sh

# ENVIRONEMENT VARIABLES
#=========================
#export SHA=`git log --oneline | cut -d ' ' -f 1 | head -n 1`
export SHA=$1

# DOCKER SERVER'S IP ADDRESS
#export XIPADDR=`ifconfig | grep -A 1 'enp0s8' | tail -n 1 | tr -s ' ' ' ' | cut -d ' ' -f 3`
export XIPADDR=$2

# RANDOM PORT based on last 5 digits of current epoch 
export XPORT=`date +%s | sed 's/[[:digit:]]\{5\}\([[:digit:]]\{5\}\).*/\1/'`
#Creates a new isolated project each time
export COMPOSE_PROJECT_NAME="sha${SHA}"
export NET_LABEL=$COMPOSE_PROJECT_NAME

# Don't use 'docker-compose run' to remove the containers, because it can't remove the running containers. 
# Use 'docker-compose down'
# docker-compose rm -f
docker-compose down
# Remove unnecessary images
docker images | grep 'none' | sed 's/\s\{2,\}/#/g' | cut -d '#' -f 3 | xargs docker rmi []
#docker network rm inet || true
docker network create $NET_LABEL || true
docker-compose up --build
exit 0;

