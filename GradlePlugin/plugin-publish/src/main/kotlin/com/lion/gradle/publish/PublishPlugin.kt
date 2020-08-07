package com.lion.gradle.publish

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.LibraryVariantOutputImpl
import com.lion.gradle.publish.constant.Plugins
import com.lion.gradle.publish.constant.Plugins.maven
import com.lion.gradle.publish.handler.AarPublishHandler
import com.lion.gradle.publish.handler.JarPublishHandler
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.buildscript
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import java.util.*

/**
 * @Author 233957
 * @Date 2020/7/27 17:10
 */
class PublishPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        PublishConfig.init(target)
        PluginManager.rootProject = target

        target.buildscript {
            repositories {
                maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
                maven(url = "https://jitpack.io")
                google()
                jcenter()
            }
        }

        target.allprojects {
            repositories {
                maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
                maven(url = "https://jitpack.io")
                maven(url = "https://dl.bintray.com/thelasterstar/maven/")
                google()
                jcenter()
            }
        }

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