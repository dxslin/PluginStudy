package com.slin.plugin.kotlin

import org.gradle.api.provider.Property
import org.gradle.api.tasks.InputDirectory

abstract class GithubCatalogExt {

    @InputDirectory
    abstract fun getGithubReposDir(): Property<String>

}