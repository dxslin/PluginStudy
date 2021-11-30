package com.slin.study.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputDirectories;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import groovy.text.TemplateEngine;

/**
 * author: slin
 * <p>
 * date: 2021/11/19
 * <p>
 * description:
 *
 * 假设您有一个任务处理不同类型的模板，例如 FreeMarker、Velocity、Moustache 等。它获取模板源文件并将它们与一些模型数据结合以生成模板文件的填充版本。
 *
 * 此任务将具有三个输入和一个输出：
 *
 * 模板源文件
 *
 * 模型数据
 *
 * 模板引擎
 *
 * 输出文件的写入位置
 *
 *
 */
public abstract class ProcessTemplatesTask extends DefaultTask {

    @Input
    public abstract Property<TemplateEngine> getTemplateEngine();

    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();

    @Nested
    public abstract TemplateData getTemplateData();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    @TaskAction
    public void processTemplate(){

    }

}
