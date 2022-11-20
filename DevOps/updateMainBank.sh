#!/bin/bash

# remove containers, then images, then volumes in docker desktop
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker system prune --all --volumes --force

# set-up as if in clean state build again
source "./buildMainBank.sh"