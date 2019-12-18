#!/bin/bash
FILE=./bin/tour
if [ -f "$FILE" ]; then
    $FILE $1 $2 $3
else
    mkdir bin
    mcs *.cs src/*.cs models/*.cs exceptions/*.cs -out:bin/tour
    $FILE $1 $2 $3
fi
