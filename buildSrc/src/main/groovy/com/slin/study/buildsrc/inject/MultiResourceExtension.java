package com.slin.study.buildsrc.inject;

import org.gradle.api.NamedDomainObjectContainer;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public abstract class MultiResourceExtension {

    abstract NamedDomainObjectContainer<Resource> getResources();

}
