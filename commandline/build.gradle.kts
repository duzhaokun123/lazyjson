plugins {
    kotlin("jvm") version "1.6.20"
    java
}

group = "io.github.duzhaokun123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":codegen"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}