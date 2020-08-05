/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler

object JarPublishHandler : BasePublishHandler() {

    private const val PUBLICATION_NAME_JAR = "jar"

    override fun getPublicationName() = PUBLICATION_NAME_JAR

    override fun getSource() = "${project.buildDir}/libs/${project.name}.jar"
}