import java.nio.charset.Charset
import java.util.*

plugins {
    `java-library`
    `kotlin-dsl`
}
apply(plugin = "com.novoda.bintray-release")

dependencies {
    api(gradleApi())
    api("com.android.tools.build:gradle:4.0.0")
    api("org.jfrog.buildinfo:build-info-extractor-gradle:4.12.0")
}

configure<com.novoda.gradle.release.PublishExtension> {
    val property = Properties()
    val localPropertiesFile = File("../local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.reader(Charset.forName("UTF-8")).run {
            property.load(this)
        }
    }


    userOrg = "ruhaly"
    groupId = "com.plugin"
    artifactId = "publish"
    publishVersion = "1.0.0"
    desc = "Oh hi, this is a nice description for a project, right?"
    website = ""
    repoName = "plugin"
    bintrayUser = property.getProperty("bintray.user")
    bintrayKey = property.getProperty("bintray.apikey")

    println("--------$bintrayUser \n----------$bintrayKey")
    dryRun = false
}




