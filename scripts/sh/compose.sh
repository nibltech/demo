#!/bin/bash

SCRIPTS_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
ROOT_DIR=${SCRIPTS_DIR}/../..

pushd ${ROOT_DIR} > /dev/null

# Combine environment files into one
COMBINED_ENV_FILE="/tmp/nibl-demo-docker.env"
rm ${COMBINED_ENV_FILE} > /dev/null 2>&1
for ENV_FILE in $(find ${ROOT_DIR} -type f -name .env)
do
    cat ${ENV_FILE} >> ${COMBINED_ENV_FILE}
    echo "" >> ${COMBINED_ENV_FILE}
done

# Start the Docker environment based on the info in the .env file
docker compose --env-file ${COMBINED_ENV_FILE} $*

popd > /dev/null