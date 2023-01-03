import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
}

application {
    mainClass.set("de.istkorrekt.gameoflife.presentation.rest.RestApplicationKt")
    applicationName = "gameoflife-rest"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation(rootProject)

    runtimeOnly("ch.qos.logback:logback-classic:1.4.5")

    testImplementation("io.rest-assured:rest-assured:5.3.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.3.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}