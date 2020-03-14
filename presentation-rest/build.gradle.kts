import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "de.istkorrekt.gameoflife.presentation.rest.RestApplication"
    applicationName = "gameoflife-rest"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.sparkjava:spark-core:2.9.1")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation(rootProject)

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}