#!/bin/bash

#list all current containers
docker ps

# testing
# used to preload the sql container with the sql script for database creation
# docker exec -i mysql mysql -u root -p wolfe2022 BankSystem2022 < '/c/Users/joshl/OneDrive/Desktop/Loyola Spring Courses/Software Engineering/Bank-System-2022/DevOps/DatabaseCreator.sql'


if [ “$TERM_PROGRAM” = “mintty” ]
then
    winpty docker exec -it mysql bin/bash
else
    docker exec -it mysql bin/bash
fi

#When root@[containerID for mysql]:/# appears:   
#TYPE: mysql -u root -p
#TYPE:wolfe2022 
#in and press ENTER even thought it will look like you are not typing on screen

#You are now logged in to the MYSQL database within the container
#To run the DatabaseSetup.sql script:
#TYPE source DatabaseSetup.sql;

#Otherwise proceed with operations to change and manipulate the database