import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "1.5.2"
    kotlin("jvm")
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
    implementation(project(":codegen"))
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
//    version.set("2021.3.3")
    localPath.set("/home/duzhaokun/App/android-studio-canary")
//    localPath.set("/home/duzhaokun/App/idea")
}
tasks {
    patchPluginXml {
        changeNotes.set("""
            first version        """.trimIndent())
    }
}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "11"
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}