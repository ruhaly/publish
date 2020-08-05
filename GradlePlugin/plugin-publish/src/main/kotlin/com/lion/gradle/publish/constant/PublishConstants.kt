/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.constant

object PublishConstants {

    const val URL_COMPANY = "https://www.lionaitech.com/"

    const val SUFFIX_SNAPSHOT = "-SNAPSHOT"
    const val SUFFIX_RELEASE = "-RELEASE"

    // 下面这部分POM的配置不是必须的，使用默认生成的也可以
    // 库名称
    const val POM_NAME = "Lion Library"

    // 库的打包格式为aar, 常见的还有jar
    const val POM_PACKAGING_AAR = "aar"

    // 库的描述
    const val POM_DESCRIPTION = "Lion Library"

    // POM文件中指向你网站的地址
    const val POM_URL =
        URL_COMPANY

    // SCM是指版本管理工具
    const val POM_SCM_URL =
        URL_COMPANY
    const val POM_SCM_CONNECTION =
        URL_COMPANY
    const val POM_SCM_DEV_CONNECTION =
        URL_COMPANY

    // 开放协议相关信息
    const val POM_LICENCE_NAME = "Apache License Version 2.0"
    const val POM_LICENCE_URL =
        URL_COMPANY
    const val POM_LICENCE_DIST =
        POM_LICENCE_NAME

    // 开发者的相关信息
    const val POM_DEVELOPER_ID = "lion"
    const val POM_DEVELOPER_NAME = "lion"
    const val POM_DEVELOPER_EMAIL = "lion@lionaitech.com"
}