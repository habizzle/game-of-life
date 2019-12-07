# Yet another implementation of Conway's Game of Life

This is another implementation of https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life.

Build and run with terminal UI:
```
$ ./gradlew :presentation-terminal:run
```

The awesome Lanterna framework (https://github.com/mabe02/lanterna), which is used for the terminal UI, tries to auto-detect, which terminal implementation should be used.
If the application is directly started via Gradle (with `run`), a Swing-based terminal implementation is used.
If you want to run the game directly in your favourite text terminal, build the application first and then run it in your terminal:

```
$ run-terminal.sh
```
or
```
$ ./gradlew :presentation-terminal:installDist
$ ./presentation-terminal/build/install/gameoflife-terminal/bin/gameoflife-terminal
```
