#!/bin/bash
set +e
. base.sh

echo Creating project
post_json "projects" '{"name": "demo"}'

echo Creating project
post_json "projects" '{"name": "sample"}'

