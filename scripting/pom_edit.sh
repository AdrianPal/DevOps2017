#!/bin/bash

#################################################
# USAGE:                                        #
# ./pom_edit.sh PROCESSOR1 PROCESSOR2 ...       #
#       -- Where PROCESSOR1, PROCESSOR2 are     #
#          processors listed in the Island Pom. #
#################################################

# Enable the script to be launched from everywhere
SOURCE="${BASH_SOURCE[0]}"
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
SRC="$DIR/src/"

# Get the island folder
ISLAND="$DIR/../island"

# Get the POM file (island folder)
POM="./pom.xml"

# TMP extension (used to avoid bugs, including on Mac OSX)
TMP_EXT=".tmp"

# Arguments for sed
ARG_SED='-i'$TMP_EXT' -e '':a'' -e ''N'' -e ''$!ba'' -e'

# Go to island folder
cd "$ISLAND"

# Comment all processors
sed ${ARG_SED} 's/<processors>.*<\/processors>/<processors><\/processors>/g' ${POM}

# Iterate over all arguments
for arg in "$@"; do
    # Uncomment the processor matching the current argument
    sed ${ARG_SED} 's/<\/processors>/<processor>'$arg'<\/processor><\/processors>/g' ${POM}
done

rm ${POM}${TMP_EXT}