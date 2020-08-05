/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler

object AarPublishHandler : BasePublishHandler() {

    private const val PUBLICATION_NAME_AAR = "aar"

    override fun getPublicationName() = PUBLICATION_NAME_AAR

    override fun getSource() =
        config.source ?: "${project.buildDir}/outputs/aar/${project.name}.aar"
}