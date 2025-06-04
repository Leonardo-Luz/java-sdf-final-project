#!/bin/bash

DIR="$HOME/documents/code/git/misc/java/java-sdf-final-project"
CURDIR=$(pwd)

cd "$DIR" || exit 1

java --enable-native-access=ALL-UNNAMED \
  -Djava.util.logging.config.file=target/classes/logging.properties \
  -cp target/java-sdf-final-project-1.0.jar:$(cat classpath.out) ifrs.edu.br.App "$@"

cd "$CURDIR" || exit 1
