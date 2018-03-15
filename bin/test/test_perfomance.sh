#!/bin/sh

IDAM_API_URL=http://localhost:4501
S2S_URL=http://localhost:4502
DM_GW_BASE_URI=http://localhost:3603
TEST_URL=http://localhost:4621
TEST_TOKEN=$(./bin/idam/idam-get-user-token.sh user1a@test.com 123 http://localhost:4501)

#####################
# PERFORMANCE TEST ##
#####################

#./gradlew performance
#./gradlew gatlingRun --info

#xdg-open build/reports/gatling/*/index.html
#open build/reports/gatling/*/index.html
#start "" build/reports/gatling/*/index.html
