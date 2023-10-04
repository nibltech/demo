ARG TAG

FROM sagcr.azurecr.io/apigateway:${TAG}

ARG SAG_HOME
ARG IS_HOME
ARG APIG_IS_HOME
ARG SRC_NIBBLE_DIR=./nibble
ARG SRC_CONFIG_DIR=./apig/config
ARG SRC_UPDATES_DIR=./updates
ARG TGT_NIBBLE_DIR=/tmp/nibble
ARG TGT_CONFIG_DIR=${APIG_IS_HOME}/config
ARG TGT_UPDATES_DIR=${IS_HOME}/updates
ARG TGT_PKGS_DIR=${APIG_IS_HOME}/packages

ENV SAG_HOME=${SAG_HOME}
ENV IS_HOME=${IS_HOME}
ENV APIG_IS_HOME=${APIG_IS_HOME}
ENV JAVA_HOME=${SAG_HOME}/jvm/jvm

# Copy configuration files
COPY --chown=sagadmin:sagadmin ${SRC_CONFIG_DIR}/licenseKey.xml ${TGT_CONFIG_DIR}

# Take ownership of the shared volume
RUN mkdir ${APIG_IS_HOME}/logs \
    && chown sagadmin ${APIG_IS_HOME}/logs \
    && echo -n > ${APIG_IS_HOME}/logs/server.log \
    && chown sagadmin ${APIG_IS_HOME}/logs/server.log

# Remove unnecessary packages
RUN cd ${TGT_PKGS_DIR} \
	&& rm -rf WmARTExtDC WmAssetPublisher WmCloud WmConsul WmFlatFile WmISExtDC WmJSONAPI WmMonitor WmXSLT

# Copy Nibble files
COPY --chown=sagadmin:sagadmin ${SRC_NIBBLE_DIR} ${TGT_NIBBLE_DIR}
COPY --chown=sagadmin:sagadmin ${SRC_CONFIG_DIR}/*.cfg ${TGT_NIBBLE_DIR}

# Install Nibble packages
USER root
RUN microdnf install -y findutils && microdnf clean all

USER sagadmin
RUN ${TGT_NIBBLE_DIR}/install.sh && rm -rf ${TGT_NIBBLE_DIR}