Yet another implementation of Conway's Game of Life
===

This is another implementation of https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life.

Prerequisites:
* JDK 11 installed

Build and run with terminal UI
---

```shell script
./gradlew :presentation-terminal:run
```

The awesome Lanterna framework (https://github.com/mabe02/lanterna), which is used for the terminal UI, tries to auto-detect, which terminal implementation should be used.
If the application is directly started via Gradle (with `run`), a Swing-based terminal implementation is used.
If you want to run the game directly in your favourite text terminal, build the application first and then run it in your terminal:

```shell script
run-terminal.sh
```
or
```shell script
./gradlew :presentation-terminal:installDist
./presentation-terminal/build/install/gameoflife-terminal/bin/gameoflife-terminal
```

Build and run with REST server
---

```shell script
./gradlew :presentation-rest:run
```

...and then use the REST client from https://github.com/habizzle/game-of-life-html5.

Build and run with REST server and Docker
---

Before the first run, build the Docker image (app is built during image creation):
```shell script
docker build --tag istkorrekt/gameoflife-rest .
```

Then run it in a container:
```shell script
docker run -p 1234:4567 istkorrekt/gameoflife-rest
```

Congrats, the rest server runs now at http://localhost:1234.