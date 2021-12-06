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
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public abstract class ProcessTemplates {

    @Input
    public abstract Property<String> getTemplateEngineType();

    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();

    @Nested
    public abstract TemplateData getTemplateData();

    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    public void templateData(Action<TemplateData> action){
        action.execute(getTemplateData());
    }


}
