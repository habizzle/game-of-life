#!/usr/bin/env sh

./gradlew :presentation-terminal:clean :presentation-terminal:distTar
tar -C "presentation-terminal/build/distributions/" -xf "presentation-terminal/build/distributions/gameoflife-terminal.tar"
./presentation-terminal/build/distributions/gameoflife-terminal/bin/gameoflife-terminal
