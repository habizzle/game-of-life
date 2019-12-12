plugins {
    application
}

application {
    mainClassName = "de.istkorrekt.gameoflife.presentation.terminal.TerminalApplication"
    applicationName = "gameoflife-terminal"
}

dependencies {
    implementation(rootProject)
    implementation("com.googlecode.lanterna:lanterna:3.0.1")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}