ARG TAG=latest

FROM ibmwebmethods.azurecr.io/universalmessaging-server:${TAG}

ARG UM_HOME
ARG CONFIG_FILES=./um/config

COPY --chown=sagadmin:sagadmin ${CONFIG_FILES}/licence.xml ${UM_HOME}/server/umserver/licence