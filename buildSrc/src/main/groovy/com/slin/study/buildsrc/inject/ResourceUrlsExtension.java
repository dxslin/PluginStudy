package com.slin.study.buildsrc.inject;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public interface ResourceUrlsExtension {

    NamedDomainObjectContainer<ResourceUrl> getResources();

}
