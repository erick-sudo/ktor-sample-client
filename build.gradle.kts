val ktorVersion: String by project

plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-websockets:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

//tasks.register<JavaExec>("run") {
//    mainClass = "org.example.Main"
//}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}