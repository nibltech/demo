#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

# Output the logs from the Docker container(s)
${SCRIPTS_DIR}/compose.sh logs $*