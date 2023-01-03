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

Build and run via CLI
---

You can run the compiled project directly with `java`. This probably makes only sense with the help of an IDE. The game can only be used in "auto play" mode via CLI.

```shell script
java ... de.istkorrekt.gameoflife.app.cli.CliApplicationKt (0|0),(1|0),(2|0) 5 3
```

This will start the game with "alive" cells at the given cartesian coordinates (`(0|0),(1|0),(2|0)`) on a 5x5 field. The game will go for 3 rounds.

The CLI mode is, of course, not much fun :) - but could be used for benchmarking (with big field, lots of rounds and a good setup, which evolves nicely). The elapsed time is printed after the game has finished.

But...why?
---

Just for fun and learning!

Credits
---

Thanks go out to the [BetterProgramming](https://medium.com/better-programming) blog, which has a very comprehensive post on [setting up Google Cloud Run with GitHub Actions](https://medium.com/better-programming/publish-your-cloud-run-app-with-github-actions-6c18ff5c5ee4).
