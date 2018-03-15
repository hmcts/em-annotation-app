#!/bin/sh

IDAM_API_URL=http://localhost:4501
S2S_URL=http://localhost:4502
TEST_URL=http://localhost:4621
TEST_TOKEN=$(./bin/idam/idam-get-user-token.sh user1a@test.com 123 http://localhost:4501)

#####################
# SMOKE TEST ########
#####################
TEST_TOKEN=$TEST_TOKEN ./gradlew smoke --info

xdg-open smokeTests/build/reports/tests/smoke/index.html
open smokeTests/build/reports/tests/smoke/index.html
start "" smokeTests/build/reports/tests/smoke/index.html
