package com.slin.study.buildsrc.inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.TaskAction;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public abstract class PrintResourceTask extends DefaultTask {

    @Nested
    abstract Property<Resource> getResource();

    @TaskAction
    public void print(){
        printResource(getResource().get());
    }

    private static void printResource(Resource resource){
        String sb = "Resource{" +
                "hostName:" + resource.getHostName().get() +
                "path: " + resource.getPath().get() +
                "}";
        System.out.println(sb);
    }

}
