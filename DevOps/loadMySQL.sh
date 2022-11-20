#!/bin/bash

# METHOD 1: load database manually
# # used to preload the sql container with the sql script for database creation
# docker exec -i mysql mysql -u root -pwolfe2022 < "/path/to/Bank-System-2022/DevOps/DatabaseCreator.sql"

# METHOD 2: Docker cp
# copy file into container
docker cp DatabaseSetup.sql mysql:/