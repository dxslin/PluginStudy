package com.slin.study.buildsrc.rename

import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.function.Consumer

/**
 * apk打包重命名
 *
 * 这里展示了根据Android配置，创建不同的依赖任务
 *
 *
 */
class ApkRenamePlugin implements Plugin<Project>{


    private Project mProject
    private RenameExtension mRenameExtension

    @Override
    void apply(Project target) {
        this.mProject = target;
        this.mRenameExtension = mProject.getExtensions().create("apkRenameExt", RenameExtension.class);

        target.afterEvaluate {
            BaseAppModuleExtension appModuleExtension = (BaseAppModuleExtension) target.getExtensions().getByName("android");
            appModuleExtension.getApplicationVariants().forEach(new Consumer<ApplicationVariant>() {
                @Override
                void accept(ApplicationVariant applicationVariant) {
                    createRenameTask(applicationVariant)
                }
            })
        }

    }

    private void createRenameTask(ApplicationVariant variant) {
        String variantName = variant.name.capitalize()
        String flavorName = variant.flavorName

        println "createRenameTask: $variantName"

        mProject.getTasks().create("apkRename" + variantName, RenameTask.class) {

            it.inputFile = mProject.layout.buildDirectory.file("\\outputs\\apk\\$flavorName\\${variant.buildType.name}\\app-$flavorName-${variant.buildType.name}.apk")
            it.rule = mRenameExtension.rule
            it.outDir = mRenameExtension.outDir
            it.group = "version"
            it.dependsOn("assemble${variant.flavorName.capitalize()}")

        }
    }
}