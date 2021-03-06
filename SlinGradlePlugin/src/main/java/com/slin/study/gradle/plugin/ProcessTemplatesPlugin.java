package com.slin.study.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * author: slin
 * <p>
 * date: 2021/11/22
 * <p>
 * description:
 */
public class ProcessTemplatesPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {

        ProcessTemplates processTemplates = project.getExtensions()
                .create("processTemplates", ProcessTemplates.class);

        project.afterEvaluate(project1 -> project1.getTasks()
                .create("processTemplates", ProcessTemplatesTask.class, task -> {
                    task.setGroup("process");   //设置分组，方便找到任务位置

                    task.getTemplateEngineType().set(processTemplates.getTemplateEngineType());
                    task.getOutputDir().set(processTemplates.getOutputDir());
                    task.setTemplateData(processTemplates.getTemplateData());
                    task.getSourceFiles().from(processTemplates.getSourceFiles());
                }));
    }

}
