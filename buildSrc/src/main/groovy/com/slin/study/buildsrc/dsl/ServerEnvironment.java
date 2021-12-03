package com.slin.study.buildsrc.dsl;

import org.gradle.api.provider.Property;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
abstract public class ServerEnvironment {
    private final String name;

    @javax.inject.Inject
    public ServerEnvironment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public Property<String> getUrl();
}