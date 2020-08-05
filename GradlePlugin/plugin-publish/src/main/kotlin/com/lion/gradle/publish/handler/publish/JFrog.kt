/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler.publish

import com.lion.gradle.publish.PublishConfig
import com.lion.gradle.publish.constant.PublishConstants
import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create
import org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention
import org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask

object JFrog : IPublishPlatform {
    override var userName = PublishConfig.config.user
    override var password = PublishConfig.config.password
    override var url = PublishConfig.config.url
    override var releasesRepoKey = "android-releases"
    override var snapshotsRepoKey = "android-snapshots"

    override fun publish(
        project: Project,
        publicationName: String,
        groupId: String,
        artifactId: String,
        version: String,
        source: String
    ) {
        project.afterEvaluate {
            val publishingExtension = project.extensions.getByType(PublishingExtension::class.java)
            publishingExtension.run {
                publications {
                    create<MavenPublication>(publicationName) {
                        this.groupId = groupId
                        this.artifactId = artifactId
                        this.version = version
                        artifact(source)

                        pom {
                            name.set(PublishConstants.POM_NAME)
                            description.set(PublishConstants.POM_DESCRIPTION)
                            url.set(PublishConstants.POM_URL)

                            licenses {
                                license {
                                    name.set(PublishConstants.POM_LICENCE_NAME)
                                    url.set(PublishConstants.POM_LICENCE_URL)
                                    distribution.set(PublishConstants.POM_LICENCE_DIST)
                                }
                            }
                            developers {
                                developer {
                                    id.set(PublishConstants.POM_DEVELOPER_ID)
                                    name.set(PublishConstants.POM_DEVELOPER_NAME)
                                    email.set(PublishConstants.POM_DEVELOPER_EMAIL)
                                }
                            }
                            scm {
                                connection.set(PublishConstants.POM_SCM_CONNECTION)
                                developerConnection.set(PublishConstants.POM_SCM_DEV_CONNECTION)
                                url.set(PublishConstants.POM_SCM_URL)
                            }
                            withXml {
                                val root = asNode()
                                root.children().last()

                                val dependenciesNode = root.appendNode("dependencies")
                                configurations.getByName("api").allDependencies.forEach { dependency ->
                                    if (dependency.group != null && dependency.version != null) {
                                        val dependencyNode =
                                            dependenciesNode.appendNode("dependency").apply {
                                                appendNode("groupId", dependency.group)
                                                appendNode("artifactId", dependency.name)
                                                appendNode("version", dependency.version)
                                            }
                                        if (dependency is ModuleDependency) {
                                            dependency.excludeRules.run {
                                                if (isNotEmpty()) {
                                                    val exclusions =
                                                        dependencyNode.appendNode("exclusions")
                                                    forEach { excludeRule ->
                                                        val exclusion =
                                                            exclusions.appendNode("exclusion")
                                                        excludeRule.group.run {
                                                            exclusion.appendNode("groupId", this)
                                                        }
                                                        excludeRule.module.run {
                                                            exclusion.appendNode("artifactId", this)
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val artifactoryTask = project.tasks.findByName("artifactoryPublish") as ArtifactoryTask
            val artifactoryPluginConvention =
                project.convention.findPlugin(ArtifactoryPluginConvention::class.java)
            artifactoryTask.run {
                publications(publicationName)

                val clientConfig = artifactoryPluginConvention?.clientConfig
                clientConfig?.run {
                    publisher.run {
                        contextUrl = JFrog.url
                        repoKey = JFrog.getRepoKey()
                        username = userName
                        password = JFrog.password
                    }
                }

            }
        }
    }
}