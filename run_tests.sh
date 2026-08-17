#!/usr/bin/env bash

BROWSER=$1
ENVIRONMENT=$2

sbt \
-Dbrowser="${BROWSER:=chrome}" \
-Denvironment="${ENVIRONMENT:=local}" \
-Dbrowser.option.headless=true \
"testOnly uk.gov.hmrc.ui.specs.*" testReport