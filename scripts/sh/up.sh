#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

rm -rf "${SCRIPTS_DIR}"/../../otel/logs
rm -rf "${SCRIPTS_DIR}"/../../grafana/tempo-data

# Bring up the Docker containers
${SCRIPTS_DIR}/compose.sh up -d $*