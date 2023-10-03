#!/bin/bash

# Change to the script directory first
pushd "$(dirname "$0")" > /dev/null

# Determine if running in an API Gateway server to choose which version of NtOtelscope to install
if [ -d ${IS_HOME}/packages/WmAPIGateway ]
then
    APIG_SERVER=true
else
    APIG_SERVER=false
fi

# Loop over the package zip files
for ZIP_FILE in *.zip
do
    # If the zip file name contains 'api' but this is not an API Gateway server, then do not install it and go to the next zip file
    if [[ ${ZIP_FILE} == *api* && ${APIG_SERVER} == false ]]
    then
        continue
    fi

    # Extract the package from the zip file and create the package directory if necessary
    PKG_NAME=$(echo ${ZIP_FILE} | cut -d '-' -f1)
    PKG_DIR=${TGT_PKGS_DIR}/${PKG_NAME}
    [ -d ${PKG_DIR} ] || mkdir -m775 ${PKG_DIR}
    
    echo -n "Extracting ${ZIP_FILE} into ${PKG_DIR}... "
    # Copy the package zip to the package directory and unzip it
    cp ${ZIP_FILE} ${PKG_DIR}
    pushd ${PKG_DIR} > /dev/null
    ${JAVA_HOME}/bin/jar -xf ${ZIP_FILE}
    rm ${ZIP_FILE}
    popd > /dev/null
    echo " done!"

    # Extract the product name from the package name
    PRODUCT=$(echo ${PKG_NAME} | cut -c 3-)
    
    # Copy license and/or configuration files matching the product name into the package's config directory
    for CONFIG_FILE in $(find . -type f \( -iname \*${PRODUCT}\*.lic -o -iname \*${PRODUCT}\*.cfg \))
    do
        echo -n "Copying file ${CONFIG_FILE} into ${PKG_DIR}/config... "
        cp ${CONFIG_FILE} ${PKG_DIR}/config
        echo "done!"
    done

    # Handle nibbleAgent.cfg
    if [[ ${PKG_NAME} == NtCommon && -f ./nibbleAgent.cfg ]]
    then
        CONFIG_FILE=./nibbleAgent.cfg
        echo -n "Copying file ${CONFIG_FILE} into ${PKG_DIR}/config... "
        cp ${CONFIG_FILE} ${PKG_DIR}/config
        echo "done!"
    fi
done

popd