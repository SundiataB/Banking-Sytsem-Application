#Welcome to the new and improved official Dockerfile for the Banking system

#we start the build with a MySQL Docker image 
FROM mysql/mysql-server AS build

ENV Dockfile = "DevOps/Dockerfile"

# Install Tomcat & openjdk 11 (openjdk has java and javac) as our second docker image layer
FROM tomcat:jdk11-openjdk

# Copy source files for our OFFICAL WAR FILE to tomcat folder structure
COPY Banking.war /usr/local/tomcat/webapps/

CMD ["catalina.sh", "run"]

EXPOSE 8080
