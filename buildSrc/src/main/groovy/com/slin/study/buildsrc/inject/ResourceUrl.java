package com.slin.study.buildsrc.inject;

import org.gradle.api.provider.Property;

import java.net.URI;

import javax.inject.Inject;

/**
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public abstract class ResourceUrl {

    private String name;

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
