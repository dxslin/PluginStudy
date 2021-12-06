package com.slin.study.buildsrc.inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.NamedDomainObjectContainer;
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

    private NamedDomainObjectContainer<ResourceUrl> resources;

    @Nested
    abstract Property<HostPath> getHostPath();

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
    }

    public static void printHostPath(HostPath hostPath) {
        String sb = "HostPath{" +
                "hostName:" + hostPath.getHostName().get() + ", " +
                "path: " + hostPath.getPath().get() +
                "}";
        System.out.println(sb);
    }

    public static void printResourceUrl(ResourceUrl resourceUrl) {
        String sb = "ResourceUrl{" +
                "name:" + resourceUrl.getName() + ", " +
                "uri:" + resourceUrl.getUri().get() + ", " +
                "aliasName: " + resourceUrl.getAliasName().getOrNull() +
                "}";
        System.out.println(sb);
    }

}
