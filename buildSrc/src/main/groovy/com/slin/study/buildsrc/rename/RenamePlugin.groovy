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
 * description: 重命名插件
 *
 * 演示了获取DSL数据和创建任务
 *
 */
class RenamePlugin implements Plugin<Project> {

    private Project mProject
    private RenameExtension mRenameExtension;

    @Override
    void apply(Project target) {
        this.mProject = target;
        this.mRenameExtension = mProject.getExtensions().create("renameExt", RenameExtension.class);

        mProject.getTasks().create("rename", RenameTask.class) {
            it.inputFile = mRenameExtension.inputFile
            it.rule = mRenameExtension.rule
            it.outDir = mRenameExtension.outDir
            it.group = "version"
        }

    }


}
