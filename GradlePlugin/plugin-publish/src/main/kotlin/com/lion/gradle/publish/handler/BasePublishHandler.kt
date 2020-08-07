/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/7/13.
 */

package com.lion.gradle.publish.handler

import com.lion.gradle.publish.PluginManager
import com.lion.gradle.publish.PublishConfig
import com.lion.gradle.publish.constant.Plugins
import org.gradle.api.Project

abstract class BasePublishHandler : IHandler {

    val defaultPublishConfig = PluginManager.pluginConfig.publish
    val config = PublishConfig.config
    lateinit var project: Project

    override fun apply(target: Project) {
        project = target
        defaultPublishConfig.publishPlatform.publish(
            target,
            getPublicationName(),
            getGroupId(),
            getArtifactId(),
            "${getVersion()}${defaultPublishConfig.publishPlatform.getSuffix()}",
            getSource()
        )
    }


    private fun getGroupId(): String {
        return config.groupId ?: AarPublishHandler.defaultPublishConfig.repository.groupId
    }

    private fun getArtifactId() = config.artifactId ?: AarPublishHandler.project.name

    private fun getVersion(): String {
        return config.version ?: AarPublishHandler.defaultPublishConfig.repository.version.name
    }

    abstract fun getPublicationName(): String

    abstract fun getSource(): String
}