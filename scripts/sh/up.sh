#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Bring up the Docker containers
${SCRIPTS_DIR}/compose.sh up -d $*