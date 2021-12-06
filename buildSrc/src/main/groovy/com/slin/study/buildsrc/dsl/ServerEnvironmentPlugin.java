package com.slin.study.buildsrc.dsl;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;

import java.util.function.Consumer;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public class ServerEnvironmentPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        ObjectFactory objects = project.getObjects();

        NamedDomainObjectContainer<ServerEnvironment> serverEnvironmentContainer =
                objects.domainObjectContainer(ServerEnvironment.class, name -> objects.newInstance(ServerEnvironment.class, name));
        project.getExtensions().add("environments", serverEnvironmentContainer);

        ServersExt serversExt = project.getExtensions().create("serversExt", ServersExt.class);

        project.afterEvaluate(project1 -> serverEnvironmentContainer.all(serverEnvironment -> {
            String env = serverEnvironment.getName();
            String capitalizedServerEnv = env.substring(0, 1).toUpperCase() + env.substring(1);
            String taskName = "deployTo" + capitalizedServerEnv;

            System.out.println("taskName = " + taskName);

            project1.getTasks().register(taskName, DeployTask.class, task -> {
                task.getUrl().set(serverEnvironment.getUrl());
                task.setGroup("version");
            });

            serversExt.getServerEnv().forEach(new Consumer<ServerEnvironment>() {
                @Override
                public void accept(ServerEnvironment serverEnvironment) {
                    System.out.println("serversExt: name = " + serverEnvironment.getName());
                }
            });

        })
        );
    }
}