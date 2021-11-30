package com.slin.study.buildsrc.rename

import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

import java.util.function.Consumer

/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description:
 */
class RenamePlugin implements Plugin<Project> {

    private Project mProject
    private RenameExtension mRenameExtension;

    @Override
    void apply(Project target) {
        this.mProject = target;
        this.mRenameExtension = mProject.getExtensions().create("renameExt", RenameExtension.class);
        mProject.task("Hello Slin") {
            println "Hello Slin"
            group = "version"
        }

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

        mProject.getTasks().create("rename" + variantName, RenameTask.class) {
            File dir = mRenameExtension.outDir.asFile.get()
            if(!dir.exists()){
                dir.mkdirs()
            }

            it.inputApk = mProject.layout.buildDirectory.file("\\outputs\\apk\\$flavorName\\${variant.buildType.name}\\app-$flavorName-${variant.buildType.name}.apk")
            it.rule = mRenameExtension.rule
            it.outDir = mRenameExtension.outDir
            it.group = "version"
            it.dependsOn("assemble${variant.flavorName.capitalize()}")

        }
    }

}
