plugins {
    application
}

application {
    mainClassName = "de.istkorrekt.gameoflife.presentation.terminal.TerminalApplication"
    applicationName = "gameoflife-terminal"
}

dependencies {
    implementation(rootProject)
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}