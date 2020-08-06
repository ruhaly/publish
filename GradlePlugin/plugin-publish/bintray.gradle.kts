apply(plugin = "com.novoda.bintray-release")
configure<com.novoda.gradle.release.PublishExtension> {
    val property = com.plugin.publish.Tools.getProperties("../local.properties")
    userOrg = "hary"
    groupId = "com.hary"
    artifactId = "gradle-plugin-publish"
    publishVersion = "1.0.0"
    desc = "Oh hi, this is a nice description for a project, right?"
    repoName = "plugin"
    bintrayUser = property.getProperty("bintray.user")
    bintrayKey = property.getProperty("bintray.apikey")

    println("--------$bintrayUser \n----------$bintrayKey")
    dryRun = false
}