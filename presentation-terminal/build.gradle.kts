plugins {
    application
    java
}

application {
    mainClassName = "de.istkorrekt.gameoflife.presentation.terminal.TerminalApplication"
    applicationName = "gameoflife-terminal"
}

dependencies {
    implementation(rootProject)
    implementation("com.googlecode.lanterna:lanterna:3.0.1")
}