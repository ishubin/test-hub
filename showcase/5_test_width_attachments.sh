#!/bin/bash
set +e
. base.sh

echo Uploading test attachments
FILE_PATH=`curl -s -X POST $API/files?name=attachment.png -F "file=@sample-test-screenshot.png" | jq -r .path`
echo Uploaded to $FILE_PATH

echo Posting failed test
post_json "projects/demo/tests" "{
    \"job\": \"API_tests\", 
    \"build\": \"1\",
    \"testName\": \"net.mindengine.tests.SearchTest\",
    \"status\": \"failed\",
    \"reportedBy\": \"jenkins\",
    \"startedDate\": \"1478295238688\",
    \"endedDate\": \"1478295254853\",
    \"error\": \"something went wrong\",
    \"reason\": \"driver crashed\",
    \"testReport\": {
        \"reportType\": \"json\",
        \"report\": {
            \"nodes\": [{
                \"name\": \"Creating driver\"
            }]
        }
    },
    \"attachments\": [{
        \"name\": \"screenshot.png\",
        \"url\": \"$FILE_PATH\"
    }]
}"
