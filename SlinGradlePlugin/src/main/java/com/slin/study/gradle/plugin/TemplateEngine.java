package com.slin.study.gradle.plugin;

import java.io.File;

/**
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public interface TemplateEngine {

    void process(File input, File output);

}
