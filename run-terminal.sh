#!/usr/bin/env sh

./gradlew :presentation-terminal:installDist
./presentation-terminal/build/install/gameoflife-terminal/bin/gameoflife-terminal
