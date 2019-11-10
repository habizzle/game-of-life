#!/usr/bin/env sh

./gradlew :presentation-terminal:clean :presentation-terminal:distTar
tar -C "presentation-terminal/build/distributions/" -xf "presentation-terminal/build/distributions/presentation-terminal.tar"
./presentation-terminal/build/distributions/presentation-terminal/bin/presentation-terminal
