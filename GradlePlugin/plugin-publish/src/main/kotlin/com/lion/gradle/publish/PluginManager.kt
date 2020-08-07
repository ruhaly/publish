/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish

import com.lion.gradle.publish.constant.PublishType
import com.lion.gradle.publish.handler.publish.Bintray
import com.lion.gradle.publish.handler.publish.JFrog
import org.gradle.api.GradleException
import org.gradle.api.Project
import java.util.*

/**
 * Common plugin manager is used to configure the plugin.
 */
object PluginManager {

    /**
     * [rootProject] late init when the root project gradle script is built.
     */
    lateinit var rootProject: Project

    private var isPublishRelease = false

    var pluginConfig: PluginConfig = createPluginConfig()

    private fun createPluginConfig(): PluginConfig {
        println("------createPluginConfig---------")
        val properties = Properties()
        try {
            properties.load(this::class.java.getResourceAsStream("/properties/common.properties"))
        } catch (e: Exception) {
            throw GradleException("Please configure common.properties correctly", e)
        }
        val groupId: String = properties.getProperty("library.group.id")

        val snapshotVersionName: String = properties.getProperty("snapshot.version.name")
        val snapshotVersionCode: Int = properties.getProperty("snapshot.version.code").toInt()

        val releaseVersionName: String = properties.getProperty("release.version.name")
        val releaseVersionCode: Int = properties.getProperty("release.version.code").toInt()

        val versionName = if (isPublishRelease) releaseVersionName else snapshotVersionName
        val versionCode = if (isPublishRelease) releaseVersionCode else snapshotVersionCode


        val pushType =
            PublishConfig.project.properties.getOrDefault("publish.type", PublishType.JFROG.name)
        println("=========pushType:$pushType=============")
        return PluginConfig(
            Publish(
                Repository(
                    groupId,
                    Version(versionName, versionCode)
                ),
                isRelease = isPublishRelease,
                publishPlatform = if (PublishType.JFROG.name == pushType) JFrog else Bintray
            )
        )
    }
}