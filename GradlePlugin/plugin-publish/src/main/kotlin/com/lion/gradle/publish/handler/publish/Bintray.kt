package com.lion.gradle.publish.handler.publish

import com.lion.gradle.publish.PublishConfig
import com.lion.gradle.publish.constant.Plugins
import com.novoda.gradle.release.PublishExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * @Author 233957
 * @Date 2020/8/6 17:04
 */
object Bintray : IPublishPlatform {

    override var user = PublishConfig.config.user
    override var password = PublishConfig.config.password
    override var url = PublishConfig.config.url
    override var releasesRepoKey = ""
    override var snapshotsRepoKey = ""

    private var userOrg: String? = PublishConfig.config.userOrg
    private var repoName: String? = PublishConfig.config.repoName
    private var apiKey: String? = PublishConfig.config.apiKey

    override fun publish(
        project: Project,
        publicationName: String,
        groupId: String,
        artifactId: String,
        version: String,
        source: String
    ) {
        project.apply {
            plugin(Plugins.bintray)
            plugin(Plugins.mavenPublish)
        }
        project.configure<PublishExtension> {
            this.userOrg = Bintray.userOrg
            this.groupId = groupId
            this.artifactId = artifactId
            publishVersion = version
            repoName = Bintray.repoName
            bintrayUser = user
            bintrayKey = apiKey
            desc = "publish bintray"
            dryRun = false
        }
    }

    override fun getSuffix(): String = ""
}