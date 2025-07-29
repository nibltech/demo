#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

if [ "${1}" == "consumer" ]; then
  # Run the APi Consumer container to submit requests to API
  ${SCRIPTS_DIR}/compose.sh run -d api-consumer --name api-consumer
else
  # Run the Nibble Demo API Postman collection 100 times waiting 1 second in between each request
  ${SCRIPTS_DIR}/compose.sh run -d newman run nibble-demo-api.postman_collection.json -n 100 --delay-request 1000
fi