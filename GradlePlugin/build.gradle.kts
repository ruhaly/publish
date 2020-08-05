buildscript {
    val kotlin_version by extra("1.3.72")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.novoda:bintray-release:0.9.2")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files

        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.12.0")

    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}