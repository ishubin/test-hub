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
    "startedDate": 1478295238688,
    "endedDate": 1478295254853
}'

echo Posting failed test
post_json "projects/demo/tests" '{
    "job": "UI_tests", 
    "build": "1",
    "testName": "net.mindengine.tests.SearchTest",
    "status": "failed",
    "reportedBy": "jenkins",
    "startedDate": 1478295238688,
    "endedDate": 1478295254853,
    "error": "something went wrong",
    "reason": "driver crashed",
    "testReport": {
        "reportType": "text",
        "report": "INFO clicking the link\nINFO clicking some other link\nERROR oops, we have an error!"
    }
}'

echo Posting failed test
post_json "projects/demo/tests" '{
    "job": "UI_tests", 
    "build": "1",
    "testName": "net.mindengine.tests.SearchTest2",
    "status": "failed",
    "reportedBy": "jenkins",
    "startedDate": 1478295238688,
    "endedDate": 1478295254853,
    "error": "something went wrong",
    "reason": "driver crashed",
    "testReport": {
        "reportType": "json",
        "report": {
            "nodes": [{
                "name": "Setting cookie: some_auth_cookie=true; path=/",
                "status": "info",
                "time": 1480693477311
            }, {
                "name": "Doing some flow",
                "status": "info",
                "time": 1480693477311,
                "nodes": [{
                    "name": "Opening page: http://example.com",
                    "status": "info"
                },{
                    "name": "Clicking some ling"
                }]
            },{
                "name": "Some random assertion error",
                "status": "error",
                "time": 1480693477311
            }]
        }
    }
}'
