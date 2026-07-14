#!/usr/bin/env bash

BROWSER=$1
ENVIRONMENT=$2

sbt \
-Dbrowser="${BROWSER:=chrome}" \
-Denvironment="${ENVIRONMENT:=qa}" \
-Dbrowser.option.headless=true \
"testOnly uk.gov.hmrc.test.ui.specs.*"
testReport