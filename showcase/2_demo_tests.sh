#!/bin/bash
set +e
. base.sh

echo Posting passed test
post_json "projects/demo/tests" '{
    "job": "UI_tests", 
    "build": "1",
    "testName": "net.mindengine.tests.LoginTest",
    "status": "passed",
    "reportedBy": "jenkins",
    "startedDate": "1478295238688",
    "endedDate": "1478295254853"
}'

echo Posting failed test
post_json "projects/demo/tests" '{
    "job": "UI_tests", 
    "build": "1",
    "testName": "net.mindengine.tests.SearchTest",
    "status": "failed",
    "reportedBy": "jenkins",
    "startedDate": "1478295238688",
    "endedDate": "1478295254853",
    "error": "something went wrong",
    "reason": "driver crashed",
    "testReport": {
        "reportType": "json",
        "report": {
            "nodes": [{
                "name": "Creating driver"
            }]
        }
    }
}'
