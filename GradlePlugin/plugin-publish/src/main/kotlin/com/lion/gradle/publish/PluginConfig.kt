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
 * @property recommendGradleVersion String Recommend gradle version.
 * @property publish Publish Publish config.
 * @property privateRepository PrivateRepository Private repository config.
 * @constructor
 */
data class PluginConfig(
    var recommendGradleVersion: String,
    val publish: Publish,
    var privateRepository: PrivateRepository
)

/**
 * Publish config.
 *
 * @property jar Repository Jar repository.
 * @property libraryAAR Repository Library AAR repository.
 * @property componentAAR Repository Component AAR repository.
 * @property isRelease Boolean Publish release or not.
 * @property publishPlatform IPublishPlatform Publish platform,default is JFrog.
 * @constructor
 */
data class Publish(
    val jar: Repository,
    val libraryAAR: Repository,
    val componentAAR: Repository,
    var isRelease: Boolean = false,
    var publishPlatform: IPublishPlatform = JFrog
)

/**
 * Private repository.
 *
 * @property pluginGroupId String Plugin group id.
 * @property libraryGroupId String Library group id.
 * @property componentGroupId String Component group id.
 * @property latestVersion String Default version is the latest version.
 * @property isRelease Boolean Use release version or not.
 * @property isDebugMode Boolean Whether to use debug mode which is used to test private repository libraries.
 * @constructor
 */
data class PrivateRepository(
    val pluginGroupId: String,
    val libraryGroupId: String,
    val componentGroupId: String,
    var latestVersion: String,
    var isRelease: Boolean = true,
    var isDebugMode: Boolean = false
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