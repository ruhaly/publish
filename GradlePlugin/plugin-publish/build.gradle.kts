val isBuildSrc = project.name == "buildSrc"

repositories {
    google()
    jcenter()
}

plugins {
    `java-library`
    `kotlin-dsl`
    `maven-publish`
//    id("com.jfrog.bintray") version ("1.8.5")
}


if (!isBuildSrc) {
    apply(plugin = "library.java")
}

dependencies {
    api(gradleApi())
    api("com.android.tools.build:gradle:4.0.0")
    api("org.yaml:snakeyaml:1.26")
    api("com.novoda:bintray-release:0.9.2")
    api("org.jfrog.buildinfo:build-info-extractor-gradle:4.12.0")
}
