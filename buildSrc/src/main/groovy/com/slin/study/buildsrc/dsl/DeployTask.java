package com.slin.study.buildsrc.dsl;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Provider;
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
    public Provider<String> url;
    // 也可以先创建Property对象，然后getUrl.set(value)
//    public Property<String> url = getProject().getObjects().property(String.class);

    @TaskAction
    public void deploy(){
        System.out.println("Deploy Start");
        System.out.println("Deploy: url = " + url.get());
        System.out.println("Deploy End");
    }


    /**
     * 注解添加在属性上面，一定要添加getter方法
     */
    public Provider<String> getUrl() {
        return url;
    }

    public void setUrl(Provider<String> url) {
        this.url = url;
    }
}
