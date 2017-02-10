#!/bin/bash

# Compile the simulation project.
# Created by Adrian PALUMBO.

SOURCE="${BASH_SOURCE[0]}"
DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
SRC="$DIR/src/"

FILE_FOLDER="$DIR/../island"
FILE="./pom.xml"

name="GTtoLT"

cd "$FILE_FOLDER"

# Comment all processors
sed -i -e 's~\(<!--\)*\(<processor>.*</processor>\)\(-->\)*~<!--\2-->~g' ${FILE}
sed -i -e "s~<!--\(<processor>.*"$name".*</processor>\)-->$~\1~g" ${FILE}