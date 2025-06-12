#!/bin/bash

mkdir -p "$HOME/.local/bin"

if mvn clean package; then
    cp bookreview.sh "$HOME/.local/bin/bookreview"
    chmod +x "$HOME/.local/bin/bookreview"
    echo "Installation Complete."
else
    echo "Build failed. Not installing."
    exit 1
fi

if mvn dependency:build-classpath -Dmdep.outputFile=classpath.out; then
    echo "Dependencies builded"
else
    echo "Build failed. Not installing."
    exit 1
fi

if ! echo "$PATH" | grep -q "$HOME/.local/bin"; then
  echo "Note: Please add '$HOME/.local/bin' to your PATH to run 'bookreview'."
fi

