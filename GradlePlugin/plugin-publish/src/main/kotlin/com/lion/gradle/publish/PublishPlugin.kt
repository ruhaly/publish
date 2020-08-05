package com.lion.gradle.publish

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.api.LibraryVariantOutputImpl
import com.lion.gradle.publish.constant.Plugins
import com.lion.gradle.publish.handler.AarPublishHandler
import com.lion.gradle.publish.handler.JarPublishHandler
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import java.util.*

/**
 * @Author 233957
 * @Date 2020/7/27 17:10
 */
class PublishPlugin : Plugin<Project> {

    private val publishPlatform = PluginManager.pluginConfig.publish.publishPlatform

    private val credentialsAction: Action<PasswordCredentials> = Action<PasswordCredentials> {
        username = publishPlatform.userName
        password = publishPlatform.password
    }


    override fun apply(target: Project) {

        PluginManager.rootProject = target

        target.allprojects {
            repositories {
                maven(url = "http://maven.aliyun.com/nexus/content/groups/public/")
                maven(url = "https://jitpack.io")
                google()

                maven(url = "http://download.flutter.io")
                maven(url = "https://dl.bintray.com/thelasterstar/maven/")

                PluginManager.repositoryUrls.forEach { repositoryUrl ->
                    maven {
                        credentials(credentialsAction)
                        url = uri(repositoryUrl.releases)
                    }
                    maven {
                        credentials(credentialsAction)
                        url = uri(repositoryUrl.snapshots)
                    }
                }

                target.repositories {
                    flatDir {
                        dirs("libs")
                    }
                }
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