package com.slin.study.gradle.plugin;

import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

/**
 * author: slin
 * <p>
 * date: 2021/11/19
 * <p>
 * description:
 */
public abstract class TemplateData {

    /**
     * get name
     * @return name
     */
    @Input
    public abstract Property<String> getName();

    /**
     * get variables
     * @return variables
     */
    @Input
    public abstract MapProperty<String, String> getVariables();

}
