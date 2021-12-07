package com.slin.study.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

import java.util.HashMap;

/**
 * author: slin
 * <p>
 * date: 2021/12/7
 * <p>
 * description:
 */
public class ProcessTemplatesFixedPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("processTemplatesFixed", ProcessTemplatesTask.class, task -> {
            task.setGroup("process");   //设置分组，方便找到任务位置

            task.getSourceFiles().from(project.getLayout().getBuildDirectory().file("hello.txt"));
            task.getTemplateEngineType().set(ReplaceTemplateEngine.class.getSimpleName());

            task.setTemplateData(new TemplateData() {
                @Override
                public Property<String> getName() {
                    Property<String> name = project.getObjects().property(String.class);
                    name.set("原神");
                    return name;
                }

                @SuppressWarnings("UnstableApiUsage")
                @Override
                public MapProperty<String, String> getVariables() {
                    MapProperty<String, String> variables = project.getObjects().mapProperty(String.class, String.class);
                    variables.put("Hello", "你好");
                    variables.put("Klee", "可莉");
                    variables.put("Amber", "安柏");
                    variables.put("Jean", "琴");
                    return variables;
                }
            });
            task.getOutputDir().set(project.getLayout().getBuildDirectory().dir("outputs"));
        });
    }
}
