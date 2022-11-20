#!/bin/bash 

#Filename: build-War.sh
#Purpose: this takes the html and java files that it can access and builds them. 
#It then placees the resources into a war file while also using a skeleton war model.
#Date last modified: 4/4/22

#set -e

BANKING_SYS_ROOT=$(git rev-parse --show-toplevel)   #sets top level of repo

#if [ $? -ne 0 ]
#then
#    echo "Not in a git repository - aborting"
#    exit 5
#elif [ ! -s "${BANKING_SYS_ROOT}/settings.sh" ]
#then
#	echo "Settings file not available - are you running this in a different repo?"
#	exit 6
#fi
#

source "${BANKING_SYS_ROOT}/settings.sh"

echo "Rebuilding the Banking System WAR file"

echo "Moving to top of Git repository"
pushd "${BANKING_SYS_ROOT}"

if uname | grep -iq MINGW # it's windows
then 
  JAVAC_CLASSPATH="build;lib/*"
else # MacOS and Linux
  JAVAC_CLASSPATH="build:lib/*"
fi

echo Building Java classes
# set -x
find "${WARFOLDER}" build -name "*.java" -exec rm -f '{}' \;
#javac -classpath 'lib/servlet-api.jar' -d build FrontEnd/Servlets/createAccountServlet.java
javac $(find . -name "*.java") -d build -classpath "${JAVAC_CLASSPATH}" 
# set +x

if [ $? -ne 0 ]
then
  echo compile failed
  exit 2
fi

# Put the class files into the skeleton WAR directory
# Java class files must be under WEB-INF/classes
# set -x
echo Copying Java classes into model WAR folder
rm -fr "${WARFOLDER}"
if $USE_WAR_MODEL
then
	cp -vR "${WAR_MODEL_FOLDER}" "${WARFOLDER}"
else
	mkdir -p "${WARFOLDER}/WEB-INF/classes"
fi
echo Copying Java compiled classes into WAR web-inf
cp -vR build/* "${WARFOLDER}/WEB-INF/classes"

echo Removing all Java source files from WAR 
find "${WARFOLDER}" -name "*.java" -exec rm -f '{}' \;

echo Copy all static HTML files and CSS files into the top level of the skeleton WAR directory
cp -vR ./FrontEnd/* "${WARFOLDER}"

echo Copy all .jar dependency files into the lib folder of the skeleton WAR directory
find . -name "*.jar" -exec cp -vR "{}" "${WARFOLDER}/WEB-INF/lib" \;

# Go into the skeleton WAR file so that 'jar' command will use it as 
# the root of the WAR

# set +x

echo "Building web archive '${WARNAME}.war' from skeleton '${WARFOLDER}'"

(
	pushd "${WARFOLDER}"; 
	jar cvf ../"${WARNAME}.war" *
)
# The parentheses create a bash "sub-shell" that goes into the WAR folder, 
# builds the WAR, then exits, leaving this script in the main repo folder
# jar "c" - create
# jar "v" - verbose
# jar "f ../Voting.war" - name of the output file is ../Voting.war

if [ "${DOCKFILE_HOME}" != "${BANKING_SYS_ROOT}" ]
then

  echo "Populating Docker context ${DOCKFILE_HOME}"
  cp -v "${WARNAME}.war" "${DOCKFILE_HOME}"
fi

popd

echo Finished building new War file