#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

pushd ${SCRIPTS_DIR}/../../spring/Orders >/dev/null
./gradlew -x test copyDependencies build
popd >/dev/null

# Build the Docker images
${SCRIPTS_DIR}/compose.sh build $*