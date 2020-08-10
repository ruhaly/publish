/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish

import com.lion.gradle.publish.handler.JarPublishHandler
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPluginExtension

class JavaLibraryPlugin : JavaLibraryPlugin() {

    override fun apply(target: Project) {
        super.apply(target)

        PublishConfig.init(target)
        PluginManager.rootProject = target

        JarPublishHandler.apply(target)

        target.extensions.getByType(JavaPluginExtension::class.java).run {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        target.dependencies.apply {
            add(
                "implementation",
                target.fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar")))
            )
        }
    }
}