package com.slin.study.buildsrc.inject;

import org.gradle.api.provider.Property;

import java.net.URI;

import javax.inject.Inject;

/**
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:对象集合传递的数据类型
 *
 * 如果是作为DSL对象集合传递的数据类型，一定要有name属性，并且构造函数传入name
 *
 * 官方文档说可以直接使用接口，并且添加只读属性name即可，但是我测试之后不行
 * 参考：https://docs.gradle.org/current/userguide/custom_gradle_types.html#collection_types
 *
 */
public abstract class ResourceUrl {

    private final String name;

    @Inject
    public ResourceUrl(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract Property<URI> getUri();

    abstract Property<String> getAliasName();

}
