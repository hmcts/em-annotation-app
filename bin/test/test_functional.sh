#!/bin/sh

IDAM_API_URL=http://localhost:4501
S2S_URL=http://localhost:4502
TEST_URL=http://localhost:4621
TEST_TOKEN=$(./bin/idam/idam-get-user-token.sh user1a@test.com 123 http://localhost:4501)

#####################
# INTEGRATION TEST ##
#####################
./gradlew functional --info

xdg-open functionalTests/build/reports/tests/functional/index.html
open functionalTests/build/reports/tests/functional/index.html
start "" functionalTests/build/reports/tests/functional/index.html
