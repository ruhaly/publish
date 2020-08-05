/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.constant

object Plugins {
    const val application = "com.android.application"
    const val library = "com.android.library"

    // kotlin doc
    const val dokka = "org.jetbrains.dokka"

    // jfrog
    const val jFrog = "com.jfrog.artifactory"
    const val mavenPublish = "maven-publish"

    // nexus
    const val maven = "maven"
    const val signing = "signing"
}