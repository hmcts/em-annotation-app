#!/bin/sh
#sudo apt-get install -y maven docker docker-compose
clear;
#      logging env vars
export ROOT_APPENDER=JSON_CONSOLE
export JSON_CONSOLE_PRETTY_PRINT=true
export REFORM_SERVICE_TYPE=java
export REFORM_SERVICE_NAME=em-annotation-app
export REFORM_TEAM=evidence
export REFORM_ENVIRONMENT=local
#      healthcheck env vars
export PACKAGES_ENVIRONMENT=local
export PACKAGES_PROJECT=evidence
export PACKAGES_NAME=em-annotation-app
export PACKAGES_VERSION=unkown
export MANAGEMENT_SECURITY_ENABLED=false
#      debug mode
#export JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

./bin/fakeversion.sh
./gradlew installDist bootRepackage

#./bin/test_dependency.sh

#./bin/test_coverage.sh

./build/install/em-annotation-app/bin/em-annotation-app
