/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.tool

import java.io.File
import java.util.*

object Tools {

    fun getProperties(propertiesFilePath: String): Properties? {
        val properties = Properties()
        val propertiesFile = File(propertiesFilePath)
        return if (propertiesFile.exists()) {
            propertiesFile.reader(java.nio.charset.Charset.forName("UTF-8")).run {
                properties.load(this)
            }
            properties
        } else {
            null
        }
    }
}