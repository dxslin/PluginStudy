package com.slin.plugin.kotlin

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecSpec
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream

class GithubCatalogPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val githubCatalogExt =
            project.extensions.create("githubCatalog", GithubCatalogExt::class.java)

        project.afterEvaluate {
            project.tasks.create("GithubCatalog", PrintGithubCatalogTask::class.java) {
                group = "version"
                val dir = githubCatalogExt.getGithubReposDir()
                    .get()
                getGithubRepos().set(File(dir))
            }
        }

    }


    abstract class PrintGithubCatalogTask : DefaultTask() {

        @Input
        abstract fun getGithubRepos(): Property<File>

        @TaskAction
        fun exe() {
            printGithubCatalog(getGithubRepos().get())
        }

        private fun printGithubCatalog(dir: File) {
            val file = File(dir, "github_catalog.txt")
            val outputStream = BufferedOutputStream(FileOutputStream(file))

            outputStream.use {
                dir.listFiles()?.let { files ->
                    files.asSequence()
                        .filter {
                            it.isDirectory && !it.listFiles { _, name -> name == ".git" }
                                .isNullOrEmpty()
                        }
                        .forEach { file ->
                            println("GithubCatalog: ${file.path}")

                            outputStream.write("${file.name}\n".toByteArray())
                            // 这里不要改写为lambda表达式，否则可能编译不过去
                            @Suppress("ObjectLiteralToLambda")
                            project.exec(object : Action<ExecSpec> {
                                override fun execute(es: ExecSpec) {
                                    es.commandLine("sh -c \"git remote -v | grep push\"".split(" "))
                                    es.workingDir = file
                                    es.standardOutput = outputStream
                                }
                            })

                        }
                }
            }

        }
    }

}