repositories {
    jcenter()
    google()
}

plugins {
    `kotlin-dsl`
}

dependencies{
    api("com.novoda:bintray-release:0.9.2")
    api("org.jfrog.buildinfo:build-info-extractor-gradle:4.12.0")
}