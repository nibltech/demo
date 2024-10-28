#!/bin/bash -x

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

pwd
pushd ${SCRIPTS_DIR}/../../spring/Orders
pwd
./gradlew -x test build
popd

# Build the Docker images
${SCRIPTS_DIR}/compose.sh build $*