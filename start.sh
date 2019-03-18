#!/usr/bin/env bash

export DISPLAY=:$DISPLAY_ID
Xvfb :$DISPLAY_ID &

if [ -z "${CUCUMBER_TAGS// }" ]
then
  mvn clean verify -Denv=qa -Dbrowser=firefox -Dwebdriver-manager=false
else
  mvn clean verify -Denv=qa -Dbrowser=firefox -Dwebdriver-manager=false -Dcucumber.options="--tags $CUCUMBER_TAGS"
fi

[ -d allure-results ] || mkdir allure-results
cp target/allure-results/* allure-results/
