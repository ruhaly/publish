/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler.publish

import com.lion.gradle.publish.PluginManager
import com.lion.gradle.publish.PublishConfig
import com.lion.gradle.publish.constant.PublishConstants
import org.gradle.api.Project

interface IPublishPlatform {
    var userName: String
    var password: String
    var url: String
    var releasesRepoKey: String
    var snapshotsRepoKey: String

    fun publish(
        project: Project,
        publicationName: String,
        groupId: String,
        artifactId: String,
        version: String,
        source: String
    )

    fun isRelease() =
        PublishConfig.config.useRelease ?: PluginManager.pluginConfig.publish.isRelease

    fun getSuffix(): String {
        return if (isRelease()) {
            PublishConstants.SUFFIX_RELEASE
        } else {
            PublishConstants.SUFFIX_SNAPSHOT
        }
    }

    fun getRepoKey(): String {
        return if (isRelease()) {
            releasesRepoKey
        } else {
            snapshotsRepoKey
        }
    }
}