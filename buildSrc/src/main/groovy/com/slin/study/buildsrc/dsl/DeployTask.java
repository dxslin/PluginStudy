package com.slin.study.buildsrc.dsl;

import com.android.build.api.variant.impl.GradleProperty;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public class DeployTask extends DefaultTask {

    @Input
    public Property<String> url = getProject().getObjects().property(String.class);

    @TaskAction
    public void deploy(){
        System.out.println("开始部署");
        System.out.println("deploy: url = " + url.get());
        System.out.println("部署完成");
    }


    public Property<String> getUrl() {
        return url;
    }

    public void setUrl(Property<String> url) {
        this.url = url;
    }
}
