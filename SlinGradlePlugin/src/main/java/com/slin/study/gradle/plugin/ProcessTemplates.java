package com.slin.study.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputDirectory;

/**
 * @author slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public abstract class ProcessTemplates {

    /**
     *
     * @return template engine type
     */
    public abstract Property<String> getTemplateEngineType();

    /**
     *
     * @return sources files
     */
    public abstract ConfigurableFileCollection getSourceFiles();

    /**
     *
     * @return template data
     */
    @Nested
    public abstract TemplateData getTemplateData();

    /**
     * output dir
     * @return output dir
     */
    public abstract DirectoryProperty getOutputDir();

    /**
     * configure template data
     * @param action action to configure template data
     */
    public void templateData(Action<? super TemplateData> action){
        action.execute(getTemplateData());
    }

    /**
     * add source files
     * @param paths source file path
     */
    public void sourceFiles(Object... paths){
        getSourceFiles().from(paths);
    }

}
