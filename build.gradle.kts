import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
    id("org.jmailen.kotlinter") version "3.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("be.zvz:KotlinInside:1.14.5")
    implementation("org.fusesource.jansi:jansi:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")


}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}