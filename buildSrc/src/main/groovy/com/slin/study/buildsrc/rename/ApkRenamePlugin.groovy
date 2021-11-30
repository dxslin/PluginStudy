package com.slin.study.buildsrc.rename

import org.gradle.api.Plugin
import org.gradle.api.Project

class ApkRenamePlugin implements Plugin<Project>{

    @Override
    void apply(Project target) {
        target.task("Apk Rename"){
            println "Hello Slin"
            group = "version"
        }
    }
}