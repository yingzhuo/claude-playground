#!/bin/sh

export JAVA="${JAVA_HOME}/bin/java"
SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
BASE_DIR="${SCRIPT_DIR}/.."

JAVA_OPT="${JAVA_OPT} -server -Xmixed"
JAVA_OPT="${JAVA_OPT} -XX:-PrintCommandLineFlags -XX:-PrintFlagsInitial -XX:-PrintFlagsFinal"
JAVA_OPT="${JAVA_OPT} -XX:ThreadStackSize=1m"
JAVA_OPT="${JAVA_OPT} -XX:InitialHeapSize=4g -XX:MinHeapSize=4g -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+UseTLAB"
JAVA_OPT="${JAVA_OPT} -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=512m -XX:+UseCompressedOops"
JAVA_OPT="${JAVA_OPT} -XX:MaxDirectMemorySize=1g"
JAVA_OPT="${JAVA_OPT} -XX:+UseG1GC -XX:MaxGCPauseMillis=300"
JAVA_OPT="${JAVA_OPT} -XX:+UsePerfData"

JAVA_OPT_EXT="${JAVA_OPT_EXT} -Djava.security.egd=file:/dev/./urandom"
JAVA_OPT_EXT="${JAVA_OPT_EXT} -Dloader.system=false"
JAVA_OPT_EXT="${JAVA_OPT_EXT} -Dloader.path=${BASE_DIR}/lib,${BASE_DIR}/config"
JAVA_OPT_EXT="${JAVA_OPT_EXT} -Dspring.config.name=application"
JAVA_OPT_EXT="${JAVA_OPT_EXT} -Dspring.config.additional-location=file:${BASE_DIR}/config/"
JAVA_OPT_EXT="${JAVA_OPT_EXT} -Dlogging.config=${BASE_DIR}/config/logback.xml"

${JAVA} \
  ${JAVA_OPT} \
  ${JAVA_OPT_EXT} \
  -jar "${BASE_DIR}/lib/core.jar" \
  --spring.profiles.active=prod

exit 0
