import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
    id("org.jmailen.kotlinter") version "3.8.0"
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "hamang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("be.zvz:KotlinInside:1.14.6")
    implementation("org.fusesource.jansi:jansi:2.4.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
//    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-server
//    implementation("org.eclipse.jetty:jetty-server:11.0.9")
//    implementation("org.eclipse.jetty:jetty-servlet:11.0.9")
// https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api
//    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
//// https://mvnrepository.com/artifact/javax.websocket/javax.websocket-api
//    compileOnly("javax.websocket:javax.websocket-api:1.1")
// https://mvnrepository.com/artifact/org.eclipse.jetty/jetty-websocket
    implementation("org.eclipse.jetty:jetty-websocket:8.2.0.v20160908")
    // https://mvnrepository.com/artifact/org.eclipse.jetty.websocket/websocket-server
    implementation("org.eclipse.jetty.websocket:websocket-server:9.4.46.v20220331")
    implementation("org.eclipse.jetty.websocket:websocket-servlet:9.4.46.v20220331")// https://mvnrepository.com/artifact/org.slf4j/log4j-over-slf4j
//    implementation("org.slf4j:slf4j-log4j12:2.0.0-alpha7")
//    implementation("org.slf4j:log4j-over-slf4j:2.0.0-alpha7")
//    implementation("org.slf4j:slf4j-jdk14:2.0.0-alpha7")
//    implementation("org.slf4j:slf4j-api:2.0.0-alpha7")
//    implementation("org.slf4j:slf4j-simple:2.0.0-alpha7")






}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
application {
    mainClass.set("MainKt")
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "hamang.Main"
    }
}
