package com.slin.study.buildsrc.inject;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public interface Resource {

    @Input
    Property<String> getHostName();

    @Input
    Property<String> getPath();

}
