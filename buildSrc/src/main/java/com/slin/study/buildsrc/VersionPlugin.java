package com.slin.study.buildsrc;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * author: slin
 * date: 2021/10/21
 * description:
 */
public class VersionPlugin implements Plugin<Project> {

    public void apply(Project target) {
        target.task("greeting", task -> {
            task.setGroup("version");
            task.doLast(task1 -> {
                System.out.println("greeting: doLast");
            });
            System.out.println("Version Plugin: Hello Slin");
        });
    }
}