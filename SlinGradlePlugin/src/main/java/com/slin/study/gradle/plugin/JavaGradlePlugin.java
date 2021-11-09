package com.slin.study.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * author: slin
 * date: 2021/10/20
 * description:
 */
public class JavaGradlePlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        target.task("helloJava", task -> {
            task.setGroup("version");
            task.doLast(task1 -> {
                System.out.println("helloJava: doLast");
            });
            System.out.println("Java Plugin: Hello Java");
        });
    }

}
