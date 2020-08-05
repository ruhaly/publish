/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish

import com.lion.gradle.publish.handler.publish.JFrog
import com.lion.gradle.publish.handler.publish.RepositoryUrl
import com.lion.gradle.publish.tool.Tools
import org.gradle.api.Project
import java.util.*

// PluginManager.kt
/**
 * Common plugin manager is used to configure the plugin.
 */
object PluginManager {

    /** Recommend gradle version [RECOMMEND_GRADLE_VERSION]. */
    private const val RECOMMEND_GRADLE_VERSION = "6.4"

    /** AAR Library module group id. */
    private const val AAR_LIBRARY_GROUP_ID = "com.lion.library"

    /** AAR component module group id. */
    private const val AAR_COMPONENT_GROUP_ID = "com.lion.component"

    /** Snapshot version name. */
    private const val AAR_SNAPSHOT_VERSION_NAME = "1.0.0"

    /** Snapshot version code. */
    private const val AAR_SNAPSHOT_VERSION_CODE = 1

    /** Release version name. */
    private const val AAR_RELEASE_VERSION_NAME = "1.0.0"

    /** Release version code. */
    private const val AAR_RELEASE_VERSION_CODE = 1

    /** Jar group id. */
    private const val JAR_GROUP_ID = "com.lion.gradle"

    /** Jar version name. */
    private const val JAR_VERSION_NAME = "1.0.0"

    /** Jar version code. */
    private const val JAR_VERSION_CODE = 1

    /** Default custom task group name. */
    const val CUSTOM_TASK_GROUP_NAME = "lion"

    /**
     * [rootProject] late init when the root project gradle script is built.
     */
    lateinit var rootProject: Project

    var isPublishRelease = false

    var commonPluginConfig: PluginConfig = createPluginConfig("/properties/common.properties")!!

    var customPluginConfig: PluginConfig? = createPluginConfig("/properties/custom.properties")

    var pluginConfig: PluginConfig = customPluginConfig ?: commonPluginConfig

    val repositoryUrls: MutableList<RepositoryUrl>
        get() = pluginConfig.publish.publishPlatform.run {
            mutableListOf(
                RepositoryUrl("${url}${snapshotsRepoKey}", "${url}${releasesRepoKey}"),
                RepositoryUrl(
                    "${url}${snapshotsRepoKey}/flutter",
                    "${url}${releasesRepoKey}/flutter"
                ),
                RepositoryUrl(
                    "${url}${snapshotsRepoKey}/com/lion",
                    "${url}${releasesRepoKey}/com/lion"
                )
            )
        }

    private fun createPluginConfig(path: String): PluginConfig? {
        val properties = Properties()
        try {
            properties.load(this::class.java.getResourceAsStream(path))
        } catch (e: Exception) {
            return null
        }
        val recommendGradleVersion: String =
            Tools.checkValue(properties["recommendGradleVersion"], RECOMMEND_GRADLE_VERSION)
        val aarLibraryGroupId: String =
            Tools.checkValue(properties["aar.library.group.id"], AAR_LIBRARY_GROUP_ID)
        val aarComponentGroupId: String =
            Tools.checkValue(properties["aar.component.group.id"], AAR_COMPONENT_GROUP_ID)
        val aarSnapshotVersionName: String =
            Tools.checkValue(properties["aar.snapshot.version.name"], AAR_SNAPSHOT_VERSION_NAME)
        val aarSnapshotVersionCode: Int =
            Tools.checkValue(properties["aar.snapshot.version.code"], AAR_SNAPSHOT_VERSION_CODE)
        val aarReleaseVersionName: String =
            Tools.checkValue(properties["aar.release.version.name"], AAR_RELEASE_VERSION_NAME)
        val aarReleaseVersionCode: Int =
            Tools.checkValue(properties["aar.release.version.code"], AAR_RELEASE_VERSION_CODE)
        val jarGroupId: String = Tools.checkValue(properties["jar.group.id"], JAR_GROUP_ID)
        val jarVersionName: String =
            Tools.checkValue(properties["jar.version.name"], JAR_VERSION_NAME)
        val jarVersionCode: Int = Tools.checkValue(properties["jar.version.code"], JAR_VERSION_CODE)

        val aarVersionName = if (isPublishRelease) aarReleaseVersionName else aarSnapshotVersionName

        val aarVersionCode = if (isPublishRelease) aarReleaseVersionCode else aarSnapshotVersionCode

        println("$path : $properties")

        return PluginConfig(
            recommendGradleVersion,
            Publish(
                jar = Repository(jarGroupId, Version(jarVersionName, jarVersionCode)),
                libraryAAR = Repository(
                    aarLibraryGroupId,
                    Version(aarVersionName, aarVersionCode)
                ),
                componentAAR = Repository(
                    aarComponentGroupId,
                    Version(aarVersionName, aarVersionCode)
                ),
                isRelease = isPublishRelease,
                publishPlatform = JFrog
            ),
            PrivateRepository(
                pluginGroupId = jarGroupId,
                libraryGroupId = aarLibraryGroupId,
                componentGroupId = aarComponentGroupId,
                latestVersion = aarVersionName,
                isRelease = false,
                isDebugMode = false
            )
        )
    }
}