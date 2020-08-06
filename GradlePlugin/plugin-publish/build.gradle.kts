plugins {
    `java-library`
    `kotlin-dsl`
    `maven-publish`
}

dependencies {
    api(gradleApi())
    api("com.android.tools.build:gradle:4.0.0")
    api("org.yaml:snakeyaml:1.26")
    api("com.novoda:bintray-release:0.9.2")
    api("org.jfrog.buildinfo:build-info-extractor-gradle:4.12.0")
}

if (properties["publish.artifactory"] == "true") {
    apply(from = "jfrog.gradle.kts")
} else {
    apply(from = "bintray.gradle.kts")
}

