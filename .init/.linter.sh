#!/bin/bash
cd /home/kavia/workspace/code-generation/airport-passenger-service-platform-26705-26716/airport_facility_service
./gradlew checkstyleMain
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

