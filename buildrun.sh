#!/bin/sh
clear;
./gradlew installDist bootRepackage; docker-compose up --build;

