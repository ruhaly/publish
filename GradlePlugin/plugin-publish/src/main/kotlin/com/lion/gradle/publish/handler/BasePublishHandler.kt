/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/7/13.
 */

package com.lion.gradle.publish.handler

import com.lion.gradle.publish.PluginManager
import com.lion.gradle.publish.constant.Plugins
import com.lion.gradle.publish.tool.Tools
import org.gradle.api.Project
import java.util.*

abstract class BasePublishHandler : IHandler {

    val publishConfig = PluginManager.pluginConfig.publish

    lateinit var project: Project

    private val properties: Properties? by lazy {
        Tools.getProperties(project.projectDir.path + "/gradle.properties")
    }

    override fun apply(target: Project) {
        project = target
        target.apply { plugin(Plugins.jFrog) }
        target.apply(mapOf("plugin" to Plugins.mavenPublish))

        publishConfig.publishPlatform.publish(
            target,
            getPublicationName(),
            getGroupIdInternal(),
            getArtifactIdInternal(),
            "${getVersionInternal()}${publishConfig.publishPlatform.getSuffix()}",
            getSource()
        )
    }

    abstract fun getPublicationName(): String
    abstract fun getGroupId(): String
    abstract fun getArtifactId(): String
    abstract fun getVersion(): String
    abstract fun getSource(): String

    private fun getGroupIdInternal(): String {
        return properties?.run {
            this["groupId"] as String
        } ?: run {
            getGroupId()
        }
    }

    private fun getArtifactIdInternal(): String {
        return properties?.run {
            this["artifactId"] as String
        } ?: run {
            getArtifactId()
        }
    }

    private fun getVersionInternal(): String {
        return properties?.run {
            this["versionName"] as String
        } ?: run {
            getVersion()
        }
    }
}