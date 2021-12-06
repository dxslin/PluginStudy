package com.slin.study.buildsrc.site;

import org.gradle.api.provider.Property;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description: 嵌套对象数据
 */
abstract public class CustomData {

    abstract public Property<String> getWebsiteUrl();

    abstract public Property<String> getVcsUrl();
}