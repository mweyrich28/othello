#!/bin/bash

# check if the jar file contains .java source files for every .class file in the de/lmu/bio/ifi/ package
# checks if there is exactly one .jar file in the ./jar/ directory of the repository
# returns exit code 0 if ok, else 1

echo "[ INFO ] check if the jar file contains the source code files.."

jar_file=$(find ./jar/ -type f -name "*.jar")

num_jars=$(echo "$jar_file" | grep -c "jar$")

if [[ $num_jars -ne 1 ]]; then
    echo "[ ERROR ] there must be exactly one jar file."
    exit 1
fi

content=$(jar -tf "$jar_file" | grep -E "^de/lmu/bio/")

# retrieve all class files without inner classes
class_files=$(echo "$content" | grep -E ".+\.class" | grep -Ev "\\\$")

has_error=0

while IFS= read -r line; do
    base_name="${line%.*}"

    num_source_files=$(echo "$content" | grep -c "$base_name\.java")

    if [[ $num_source_files -eq 0 ]]; then
        echo "[ ERROR ] no source file found for class <$line>"
        has_error=1
    fi

done <<< "$class_files"

if [[ $has_error -eq 0 ]]; then
    echo "[ OK ] all source files found."
    exit 0
else
    echo "[ ERROR ] not all source files found."
    exit 1
fi