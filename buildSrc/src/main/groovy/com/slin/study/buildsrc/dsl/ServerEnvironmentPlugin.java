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
 *
 *  这个插件展示了DSL对象集合如何使用
 *
 */
public class ServerEnvironmentPlugin implements Plugin<Project> {
    @Override
    public void apply(final Project project) {
        ObjectFactory objects = project.getObjects();

        // 构造对象集合数据，可以传factory，也可以不传
        NamedDomainObjectContainer<ServerEnvironment> serverEnvironmentContainer =
                objects.domainObjectContainer(ServerEnvironment.class, name -> objects.newInstance(ServerEnvironment.class, name));
        // 添加名为`environments`扩展属性，在`environments`里面便可以添加ServerEnvironment对象集合的配置
        project.getExtensions().add("environments", serverEnvironmentContainer);

        // 另一种配置方式，先将对象集合配置到扩展里面，然后再配置对象集合
        ServersExt serversExt = project.getExtensions().create("serversExt", ServersExt.class);

        project.afterEvaluate(project1 -> serverEnvironmentContainer.all(serverEnvironment -> {
            String env = serverEnvironment.getName();
            String capitalizedServerEnv = env.substring(0, 1).toUpperCase() + env.substring(1);
            String taskName = "deployTo" + capitalizedServerEnv;

            System.out.println("taskName = " + taskName);

            project1.getTasks().register(taskName, DeployTask.class, task -> {
                task.setUrl(serverEnvironment.getUrl());
                task.setGroup("version");
            });

            serversExt.getServerEnv().forEach(serverEnvironment1 -> System.out.println("serversExt: name = " + serverEnvironment1.getName()));

        })
        );
    }
}