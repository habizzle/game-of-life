plugins {
    application
}

application {
    mainClass.set("de.istkorrekt.gameoflife.presentation.terminal.TerminalApplication")
    applicationName = "gameoflife-terminal"
}

dependencies {
    implementation(rootProject)
    implementation("com.googlecode.lanterna:lanterna:3.1.1")
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}