#!/bin/bash

export DISPLAY=:$DISPLAY_ID
Xvfb :$DISPLAY_ID &

if [ -z "${CUCUMBER_TAGS// }" ]
then
  mvn verify -Denv=dev -Dbrowser=firefox -Dwebdriver-manager=false
else
  mvn verify -Denv=dev -Dbrowser=firefox -Dwebdriver-manager=false -Dcucumber.options="--tags $CUCUMBER_TAGS"
fi
