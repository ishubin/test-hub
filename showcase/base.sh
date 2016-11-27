#!/bin/bash

export API=http://localhost:8080/api

post_json() {
    curl -X POST "$API/$1" -H "Content-Type: application/json" -d "$2"
}

