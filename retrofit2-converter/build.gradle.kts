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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation(project(":annotation"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}