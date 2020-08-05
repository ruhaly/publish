/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler

object AarPublishHandler : BasePublishHandler() {

    private const val PUBLICATION_NAME_AAR = "aar"

    override fun getPublicationName() = PUBLICATION_NAME_AAR

    override fun getGroupId(): String {
        val prefix = project.name.split("-")
        return when (prefix[0]) {
            "library" -> publishConfig.libraryAAR.groupId
            "component" -> publishConfig.componentAAR.groupId
            else -> ""
        }
    }

    override fun getArtifactId() = project.name

    override fun getVersion(): String {
        val prefix = project.name.split("-")
        return when (prefix[0]) {
            "library" -> publishConfig.libraryAAR.version.name
            "component" -> publishConfig.componentAAR.version.name
            else -> ""
        }
    }

    override fun getSource() = "${project.buildDir}/outputs/aar/${project.name}.aar"
}