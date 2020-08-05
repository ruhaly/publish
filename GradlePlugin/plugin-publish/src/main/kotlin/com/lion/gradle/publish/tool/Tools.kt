/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.tool

import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import java.io.File
import java.io.FileReader
import java.text.SimpleDateFormat
import java.util.*

object Tools {

    val systemName = System.getProperty("os.name").toLowerCase(Locale.ROOT)

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

    fun getResourceFile(destinationDir: String, resourceFileName: String): File {
        val file = File(destinationDir, resourceFileName)
        if (file.exists()) {
            return file
        }
        val inputStream = this::class.java.getResourceAsStream("/$resourceFileName")
        val byteArray = inputStream.readBytes()
        FileUtils.writeByteArrayToFile(file, byteArray)
        return file
    }

    fun getReleaseTime(): String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
    }

    fun getBranchInfo(project: Project): String {
        val result = ""
        val file = File("${project.rootDir}/.git/HEAD")
        if (!file.exists()) {
            return result
        }
        var fileReader: FileReader? = null
        try {
            fileReader = FileReader(file)
            fileReader.forEachLine {
                result.plus(it)
            }
        } catch (e: Exception) {
            return e.javaClass.name + ":" + e.message + ":" + file.absolutePath
        } finally {
            fileReader?.run {
                try {
                    this.close()
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }
        return result
    }

    fun getString(any: Any) = "\"" + any + "\""

    fun createPath(vararg args: String): String {
        val size = args.size
        val path = StringBuilder()
        for (i in 0 until size) {
            path.append(args[i])
            if (i != size - 1) {
                path.append(File.separator)
            }
        }
        return path.toString()
    }

    inline fun <reified T> checkValue(value: Any?, defaultValue: T): T {
        return value?.run {
            try {
                T::class.java.cast(this)
            } catch (e: Throwable) {
                defaultValue
            }
        } ?: run {
            defaultValue
        }
    }

    fun isWindows(): Boolean {
        println("---------------current os name: ${System.getProperty("os.name")}")
        return System.getProperty("os.name").toLowerCase().contains("windows")
    }
}