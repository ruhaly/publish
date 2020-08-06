apply(plugin = "com.jfrog.artifactory")

afterEvaluate {
    val groupId = "com.lion.gradle"
    val artifactId = project.name
    val version = "1.0.0"
    val source = "${project.buildDir}/libs/${project.name}.jar"
    JFrog.publish(project, groupId, artifactId, version, source)
}

object JFrog {
    private val properties = com.plugin.publish.Tools.getProperties("../local.properties")
    private val url: String = properties.getProperty("frog.url", "")
    private val userName: String = properties.getProperty("frog.user", "")
    private val password = properties.getProperty("frog.pwd", "")
    private const val publicationName = "jar"

    fun publish(
        project: Project,
        groupId: String,
        artifactId: String,
        version: String,
        source: String
    ) {
        project.afterEvaluate {
            val publishingExtension =
                project.extensions.getByType(PublishingExtension::class.java)
            publishingExtension.run {
                publications {
                    create<MavenPublication>(publicationName) {
                        this.groupId = groupId
                        this.artifactId = artifactId
                        this.version = version
                        artifact(source)

                        pom {
                            name.set(PublishConstants.POM_NAME)
                            description.set(PublishConstants.POM_DESCRIPTION)
                            url.set(PublishConstants.POM_URL)

                            licenses {
                                license {
                                    name.set(PublishConstants.POM_LICENCE_NAME)
                                    url.set(PublishConstants.POM_LICENCE_URL)
                                    distribution.set(PublishConstants.POM_LICENCE_DIST)
                                }
                            }
                            developers {
                                developer {
                                    id.set(PublishConstants.POM_DEVELOPER_ID)
                                    name.set(PublishConstants.POM_DEVELOPER_NAME)
                                    email.set(PublishConstants.POM_DEVELOPER_EMAIL)
                                }
                            }
                            scm {
                                connection.set(PublishConstants.POM_SCM_CONNECTION)
                                developerConnection.set(PublishConstants.POM_SCM_DEV_CONNECTION)
                                url.set(PublishConstants.POM_SCM_URL)
                            }
                            withXml {
                                val root = asNode()
                                root.children().last()

                                val dependenciesNode = root.appendNode("dependencies")
                                configurations.getByName("api").allDependencies.forEach { dependency ->
                                    if (dependency.group != null && dependency.version != null) {
                                        val dependencyNode =
                                            dependenciesNode.appendNode("dependency").apply {
                                                appendNode("groupId", dependency.group)
                                                appendNode("artifactId", dependency.name)
                                                appendNode("version", dependency.version)
                                            }
                                        if (dependency is ModuleDependency) {
                                            dependency.excludeRules.run {
                                                if (isNotEmpty()) {
                                                    val exclusions =
                                                        dependencyNode.appendNode("exclusions")
                                                    forEach { excludeRule ->
                                                        val exclusion =
                                                            exclusions.appendNode("exclusion")
                                                        excludeRule.group.run {
                                                            exclusion.appendNode(
                                                                "groupId",
                                                                this
                                                            )
                                                        }
                                                        excludeRule.module.run {
                                                            exclusion.appendNode(
                                                                "artifactId",
                                                                this
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val artifactoryTask =
                project.tasks.findByName("artifactoryPublish") as org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask
            val artifactoryPluginConvention =
                project.convention.findPlugin(org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention::class.java)
            artifactoryTask.run {
                publications(publicationName)
                val clientConfig = artifactoryPluginConvention?.clientConfig
                clientConfig?.run {
                    publisher.run {
                        contextUrl = JFrog.url
                        repoKey = JFrog.getRepoKey()
                        username = userName
                        password = JFrog.password
                    }
                }

            }
        }
    }


    private fun getRepoKey(): String {
        return "android-snapshots"
    }
}


object PublishConstants {

    private const val URL_COMPANY = "https://www.lionaitech.com/"

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
    const val POM_URL = URL_COMPANY

    // SCM是指版本管理工具
    const val POM_SCM_URL = URL_COMPANY
    const val POM_SCM_CONNECTION = URL_COMPANY
    const val POM_SCM_DEV_CONNECTION = URL_COMPANY

    // 开放协议相关信息
    const val POM_LICENCE_NAME = "Apache License Version 2.0"
    const val POM_LICENCE_URL = URL_COMPANY
    const val POM_LICENCE_DIST = POM_LICENCE_NAME

    // 开发者的相关信息
    const val POM_DEVELOPER_ID = "lion"
    const val POM_DEVELOPER_NAME = "lion"
    const val POM_DEVELOPER_EMAIL = "lion@lionaitech.com"
}
