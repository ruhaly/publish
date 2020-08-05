/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/7/13.
 */

package com.lion.gradle.publish

import com.lion.gradle.publish.handler.publish.IPublishPlatform
import com.lion.gradle.publish.handler.publish.JFrog


/**
 * Plugin config.
 *
 * @property publish Publish Publish config.
 * @constructor
 */
data class PluginConfig(
    val publish: Publish
)

/**
 * Publish config.
 *
 * @property jar Repository Jar repository.
 * @property aar Repository Library AAR repository.
 * @property isRelease Boolean Publish release or not.
 * @property publishPlatform IPublishPlatform Publish platform,default is JFrog.
 * @constructor
 */
data class Publish(
    val repository: Repository,
    var isRelease: Boolean = false,
    var publishPlatform: IPublishPlatform = JFrog
)

/**
 * Private repository.
 *
 * @property jarGroupId String Jar group id.
 * @property aarGroupId String AAR group id.
 * @property latestVersion String Default version is the latest version.
 * @property isRelease Boolean Use release version or not.
 * @constructor
 */
data class PrivateRepository(
    val jarGroupId: String,
    val aarGroupId: String,
    var latestVersion: String,
    var isRelease: Boolean = true
)

/**
 * Repository.
 *
 * @property groupId String Group id.
 * @property version Version Version info.
 * @constructor
 */
data class Repository(
    val groupId: String,
    val version: Version
)

/**
 * Repository version.
 *
 * @property name String Version name.
 * @property code Int Version code.
 * @constructor
 */
data class Version(
    val name: String,
    val code: Int
)