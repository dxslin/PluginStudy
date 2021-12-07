package com.slin.study.buildsrc.inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public abstract class PrintResourceTask extends DefaultTask {

    private NamedDomainObjectContainer<ResourceUrl> resources;

    @InputFiles
    abstract ConfigurableFileCollection getInputFiles();

    @Nested
    abstract Property<HostPath> getHostPath();

    @Input
    @Nested
    NamedDomainObjectContainer<ResourceUrl> getResources() {
        return resources;
    }


    public void setResources(NamedDomainObjectContainer<ResourceUrl> resources) {
        this.resources = resources;
    }

    @TaskAction
    public void print() {
        printHostPath(getHostPath().get());
        resources.forEach(PrintResourceTask::printResourceUrl);

        getInputFiles().forEach(file -> System.out.println("file: " + file));

    }

    public static void printHostPath(HostPath hostPath) {
        String sb = "HostPath{" +
                "hostName:" + hostPath.getHostName().getOrNull() + ", " +
                "path: " + hostPath.getPath().getOrNull() +
                "}";
        System.out.println(sb);
    }

    public static void printResourceUrl(ResourceUrl resourceUrl) {
        String sb = "ResourceUrl{" +
                "name:" + resourceUrl.getName() + ", " +
                "uri:" + resourceUrl.getUri().getOrNull() + ", " +
                "aliasName: " + resourceUrl.getAliasName().getOrNull() +
                "}";
        System.out.println(sb);
    }

}
