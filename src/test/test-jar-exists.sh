#!/bin/bash

# check that only one jar file exists within the ./jar/ directory of the repository

echo "[ INFO ] checking the ./jar/ directory for exactly one *.jar file.."

num_jars=$(find ./jar/ -type f -name "*.jar" | grep -c "jar$")

echo "[ INFO ] found $num_jars jar files"

if [[ $num_jars -eq 1 ]]; then
    echo "[ OK ] exactly one jar file found."
    exit 0
elif [[ $num_jars -eq 0 ]]; then
    echo "[ ERROR ] no jar file found."
    exit 1
else
    echo "[ ERROR ] multiple jar files found."
    exit 1
fi;

