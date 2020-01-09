import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.60"
}

repositories {
    mavenCentral()
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {

    compile("org.jetbrains.kotlin:kotlin-stdlib")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("no.tornado:tornadofx:1.7.19")

    implementation("org.apache.commons:commons-csv:1.7")
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Playground"
        attributes["Implementation-Version"] = "2019.0.1"
        attributes["Main-Class"] = "LauncherKt"
    }
    from(configurations.runtime.get().map{ if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}