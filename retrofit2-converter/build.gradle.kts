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
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation(project(":annotation"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}