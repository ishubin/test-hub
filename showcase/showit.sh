#!/bin/bash

set +e

API=http://localhost:4567/api

post_json() {
    curl -X POST "$API/$1" -H "Content-Type: application/json" -d "$2"
}

echo Creating project
post_json "projects" '{"name": "demo"}'

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
