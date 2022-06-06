package com.slin.study.buildsrc.download;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public interface HostPath {

    @Input
    Property<String> getHostName();

    @Input
    Property<String> getPath();

}
