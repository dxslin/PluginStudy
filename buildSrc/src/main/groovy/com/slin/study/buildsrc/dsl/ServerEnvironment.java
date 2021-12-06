package com.slin.study.buildsrc.dsl;

import org.gradle.api.provider.Property;

import javax.inject.Inject;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:对象集合传递的数据类型
 *
 * 如果是作为DSL对象集合传递的数据类型，一定要有name属性，并且构造函数传入name
 *
 * 官方文档说可以直接使用接口，并且添加只读属性name即可，但是我测试之后不行
 * 参考：https://docs.gradle.org/current/userguide/custom_gradle_types.html#collection_types
 *
 */
abstract public class ServerEnvironment {

    private final String name;

    @Inject
    public ServerEnvironment(String name) {
        this.name = name;
    }


    // 一定要有getName方法
    public String getName() {
        return name;
    }

    abstract public Property<String> getUrl();
}