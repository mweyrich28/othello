#!/bin/bash

echo "[ INFO ] check if the jar file is executable.."

jar_file=$(find ./jar/ -type f -name "*.jar")

num_jars=$(echo "$jar_file" | grep -c "jar$")

if [[ $num_jars -ne 1 ]]; then
    echo "[ ERROR ] there must be exactly one jar file."
    exit 1
fi

# a manifest is a neccessary condition for it to be an executable jar file
if jar -tf "$jar_file" | grep -q "^META-INF/MANIFEST.MF$"; then
    echo "[ INFO ] jar file contains a manifest."
else
    echo "[ ERROR ] jar file does not contain a manifest."
    exit 1
fi

# timeout the application because sometimes the process waits for user input (ignore program stdout)
timeout --foreground 5s java -jar "$jar_file" > /dev/null

# the exit code of the jar execution
exit_code=$?

# the exit code 124 indicates that the process was terminated by timeout -> jar is executable
if [[ $exit_code -eq 124 ]]; then
    exit_code=0
fi

if [[ $exit_code -eq 0 ]]; then
    echo "[ OK ] jar file is executable."
    exit 0
else
    echo "[ ERROR ] jar file is not executable."
    exit 1
fi