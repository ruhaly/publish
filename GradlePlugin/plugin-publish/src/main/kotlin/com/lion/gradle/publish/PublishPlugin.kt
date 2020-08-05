package com.lion.gradle.publish

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.LibraryVariantOutputImpl
import com.lion.gradle.publish.constant.Plugins
import com.lion.gradle.publish.handler.AarPublishHandler
import com.lion.gradle.publish.handler.JarPublishHandler
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

/**
 * @Author 233957
 * @Date 2020/7/27 17:10
 */
class PublishPlugin : Plugin<Project> {

    override fun apply(target: Project) {

        PublishConfig.init(target)
        PluginManager.rootProject = target

        if (target.plugins.hasPlugin(Plugins.library)) {
            val libraryExtension = target.extensions.getByType(LibraryExtension::class.java)
            libraryExtension.libraryVariants.all {
                outputs.all {
                    if (this is LibraryVariantOutputImpl) {
                        outputFileName =
                            if (buildType.name.toLowerCase(Locale.getDefault()) == "debug") {
                                "${target.name}-debug.aar"
                            } else {
                                "${target.name}.aar"
                            }
                    }
                }
            }
            AarPublishHandler.apply(target)
        } else {
            JarPublishHandler.apply(target)
        }
    }
}