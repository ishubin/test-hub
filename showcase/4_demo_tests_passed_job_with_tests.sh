#!/bin/bash
set +e
. base.sh

echo Posting passed test
post_json "projects/demo/tests" '{
    "job": "DB_tests", 
    "build": "1",
    "testName": "net.mindengine.tests.LoginTest",
    "status": "passed",
    "reportedBy": "jenkins",
    "startedDate": "1478295238688",
    "endedDate": "1478295254853"
}'

