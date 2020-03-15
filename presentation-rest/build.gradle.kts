import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

application {
    mainClassName = "de.istkorrekt.gameoflife.presentation.rest.RestApplicationKt"
    applicationName = "gameoflife-rest"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.sparkjava:spark-core:2.9.1")
    implementation("com.google.code.gson:gson:2.8.6")
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation(rootProject)

    runtimeOnly("ch.qos.logback:logback-classic:1.2.3")

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