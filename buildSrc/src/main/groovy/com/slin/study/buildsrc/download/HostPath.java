package com.slin.study.buildsrc.download;

import org.gradle.api.provider.Property;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public interface HostPath {

    Property<String> getHostName();

    Property<String> getPath();

}
