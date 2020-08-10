package com.lion.gradle.publish

import com.google.gson.Gson
import com.lion.gradle.publish.constant.PublishTarget
import com.lion.gradle.publish.tool.Tools
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * @Author 233957
 * @Date 2020/8/3 15:57
 */
object PublishConfig {

    lateinit var project: Project

    lateinit var config: Config

    fun init(project: Project) {
        this.project = project
        loadConfig()
    }

    private val properties: Properties? by lazy {
        Tools.getProperties(project.projectDir.parentFile.path + "/local.properties")
    }

    private fun loadConfig() {
        val yaml = Yaml()
        config = try {
            val config = yaml.loadAs(
                FileInputStream(File("${project.projectDir.path}/config.yml")),
                Config::class.java
            )
            properties?.apply {
                config.user = getProperty("user", "")
                config.password = getProperty("password", "")
                config.apiKey = getProperty("apiKey", "")
                config.publishTarget = getProperty("publishTarget", PublishTarget.JFROG.name)
            }
            config
        } catch (e: Exception) {
            throw GradleException("Please configure config.yml correctly", e)
        } as Config
    }
}

class Config {
    var version: String? = null
    var groupId: String? = null
    var artifactId: String? = null
    var useRelease: Boolean? = null
    var source: String? = null

    lateinit var url: String
    lateinit var user: String
    var password: String? = null
    var userOrg: String? = null
    var repoName: String? = null
    var apiKey: String? = null
    var publishTarget: String? = null

    override fun toString(): String {
        return Gson().toJson(this)
    }
}