#!/bin/bash

#############################
# USAGE:                    #
# ./compile.sh              #
#---------------------------#
# Warning, processors'      #
# package must be :         #
# spoon.processors          #
#############################

# Enable the script to be launched from everywhere
SOURCE="${BASH_SOURCE[0]}"
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
SRC="$DIR"

PROJECT="$DIR/.."
OUTPUT="$DIR/../output"
ISLAND="$DIR/../island" # Get the island folder
DEVOPS="$DIR/../devops" # Get the devops folder

SURFIRE="$ISLAND/target"    # The surefire folder where the report is put
SURFIRE_REPORT="$SURFIRE/site/surefire-report.html" # The report file of surefire

POM="$ISLAND/pom.xml"   # Get the POM file (island folder)
PROC="$DEVOPS/src/main/java/spoon/processors"   # Get the POM file (island folder)
PACKAGE="spoon.processors." # The package for the processors

TMP_EXT=".tmp"          # TMP extension (used to avoid bugs, including on Mac OSX)
ARG_SED="-i$TMP_EXT -e" # Arguments for sed

# ------------------------------------------------------
cd ${PROJECT}

for file in "$PROC"/*.java; do
    filename=$(basename "$file")
    extension="${filename##*.}"
    filename="${filename%.*}"

    # Edit the pom...
    ${SRC}/pom_edit.sh ${PACKAGE}${filename}

    # Proceed the job + report
    mvn surefire-report:report > ${OUTPUT}/${filename}_mvn.txt

    # Move and rename report
    mv ${SURFIRE_REPORT} ${OUTPUT}/${filename}_report.html

    # Remove temporary folder of Surefire
    rm -r ${SURFIRE}

    # Says it's ok!
    echo
    echo "+++++++++++++++++++++++++++++++"
    echo " ${filename} done!"
    echo "+++++++++++++++++++++++++++++++"
    echo
    echo
done