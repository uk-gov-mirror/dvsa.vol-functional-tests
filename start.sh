#!/usr/bin/env bash

export DISPLAY=:$DISPLAY_ID
Xvfb :$DISPLAY_ID &

if [ -z "${CUCUMBER_TAGS// }" ]
then
  mvn verify $optional_params_args -Dbrowser=firefox -Dwebdriver-manager=false
else
  mvn verify $optional_params_args -Dbrowser=firefox -Dwebdriver-manager=false -Dcucumber.options="--tags $CUCUMBER_TAGS"
fi