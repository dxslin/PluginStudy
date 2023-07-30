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

    /**
     * process file
     * @param input input
     * @param output output
     */
    void process(File input, File output);

}
