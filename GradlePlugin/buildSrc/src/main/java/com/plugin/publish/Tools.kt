package com.plugin.publish

import java.io.File
import java.nio.charset.Charset
import java.util.*

/**
 * @Author 233957
 * @Date 2020/8/6 14:35
 */
object Tools {
    fun getProperties(path: String): Properties {
        val property = Properties()
        val localPropertiesFile = File(path)
        if (localPropertiesFile.exists()) {
            localPropertiesFile.reader(Charset.forName("UTF-8")).run {
                property.load(this)
            }
        }
        return property
    }
}