#!/bin/bash

rm Banking.war

# create war file and move to DevOps
source "./moving-War.sh"

# remove containers, then images, then volumes in docker desktop
#docker stop $(docker ps -a -q)
#docker rm $(docker ps -a -q)
#docker rmi $(docker images -a -q)
#docker volume prune -f

cd DevOps
# setup new container and run in detach mode (i.e. in the background)
source "./runMainBank.sh"

# setup the database in the SQL container
source "./loadMySQL.sh"