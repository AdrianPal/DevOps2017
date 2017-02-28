#!/bin/bash

#############################
# USAGE:                    #
# ./compile.sh              #
#############################

# Enable the script to be launched from everywhere
SOURCE="${BASH_SOURCE[0]}"
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
SRC="$DIR"

PROJECT="$DIR/.."
OUTPUT="$DIR/../output"
ISLAND="$DIR/../island" # Get the island folder
DEVOPS="$DIR/../devops" # Get the devops folder

POM="$ISLAND/pom.xml"   # Get the POM file (island folder)
PROC="$DEVOPS/src/main/java/spoon/processors"   # Get the POM file (island folder)
PACKAGE="spoon.processors."

TMP_EXT=".tmp"          # TMP extension (used to avoid bugs, including on Mac OSX)
ARG_SED="-i$TMP_EXT -e" # Arguments for sed

# ------------------------------------------------------
cd ${PROJECT}

for file in "$PROC"/*.java; do
    filename=$(basename "$file")
    extension="${filename##*.}"
    filename="${filename%.*}"

    ${SRC}/pom_edit.sh ${PACKAGE}${filename}
    mvn clean test > ${OUTPUT}/${filename}_result.txt
    echo "+ ${filename} done!"
    echo
done