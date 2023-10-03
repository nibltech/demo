ARG TAG

FROM sagcr.azurecr.io/webmethods-microservicesruntime:${TAG}

ARG SAG_HOME
ARG IS_HOME
ARG SRC_NIBBLE_DIR=./nibble
ARG SRC_CONFIG_DIR=./msr/config
ARG SRC_UPDATES_DIR=./updates
ARG SRC_PKGS_DIR=./msr/packages
ARG TGT_NIBBLE_DIR=/tmp/nibble
ARG TGT_CONFIG_DIR=${IS_HOME}/config
ARG TGT_UPDATES_DIR=${IS_HOME}/updates
ARG TGT_PKGS_DIR=${IS_HOME}/packages

ENV SAG_HOME=${SAG_HOME}
ENV IS_HOME=${IS_HOME}
ENV JAVA_HOME=${SAG_HOME}/jvm/jvm

# Copy configuration files
COPY --chown=sagadmin:sagadmin ${SRC_CONFIG_DIR}/licenseKey.xml ${TGT_CONFIG_DIR}
COPY --chown=sagadmin:sagadmin ${SRC_CONFIG_DIR}/messaging.cnf ${TGT_CONFIG_DIR}

# Replace the startContainer script
COPY --chown=sagadmin:sagadmin ./msr/startContainer.sh ${IS_HOME}/bin/startContainer.sh

# Apply updates
RUN chown sagadmin ${TGT_UPDATES_DIR}
COPY --chown=sagadmin:sagadmin ${SRC_UPDATES_DIR}/multirelease.jar ${TGT_UPDATES_DIR}

# Take ownership of the shared volume
RUN mkdir ${IS_HOME}/logs && chown sagadmin ${IS_HOME}/logs
RUN echo -n > ${IS_HOME}/logs/server.log && chown sagadmin ${IS_HOME}/logs/server.log

# Remove unnecessary packages
RUN cd ${TGT_PKGS_DIR} \
	&& rm -rf WmARTExtDC WmAssetPublisher WmCloud WmConsul WmFlatFile WmISExtDC WmJSONAPI WmMonitor WmXSLT

# Copy Nibble files
COPY --chown=sagadmin:sagadmin ${SRC_NIBBLE_DIR} ${TGT_NIBBLE_DIR}
COPY --chown=sagadmin:sagadmin ${SRC_CONFIG_DIR}/*.cfg ${TGT_NIBBLE_DIR}

# Install Nibble packages
USER root
RUN apk add bash
USER sagadmin
RUN ${TGT_NIBBLE_DIR}/install.sh && rm -rf ${TGT_NIBBLE_DIR}

# Install NtDemo package
COPY --chown=sagadmin:sagadmin ${SRC_PKGS_DIR}/NtDemo ${IS_HOME}/packages/NtDemo
RUN ${IS_HOME}/bin/jcode.sh make NtDemo