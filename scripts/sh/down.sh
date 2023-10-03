#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Bring down the Docker containers
${SCRIPTS_DIR}/compose.sh down $*