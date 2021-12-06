package com.slin.study.buildsrc.dsl;

import org.gradle.api.NamedDomainObjectContainer;

/**
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public interface ServersExt {

    NamedDomainObjectContainer<ServerEnvironment> getServerEnv();

}
