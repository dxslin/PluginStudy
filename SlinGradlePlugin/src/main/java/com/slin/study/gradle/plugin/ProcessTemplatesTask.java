package com.slin.study.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * author: slin
 * <p>
 * date: 2021/11/19
 * <p>
 * description:
 * <p>
 * 假设您有一个任务处理不同类型的模板，它获取模板源文件并将它们与一些模型数据结合以生成模板文件的填充版本。
 * <p>
 * 此任务将具有三个输入和一个输出：
 * <p>
 * 模板源文件
 * <p>
 * 模型数据
 * <p>
 * 模板引擎
 * <p>
 * 输出文件的写入位置
 */
public abstract class ProcessTemplatesTask extends DefaultTask {

    /**
     *
     * @return template engine type
     */
    @Input
    public abstract Property<String> getTemplateEngineType();
    /**
     *
     * @return sources files
     */
    @InputFiles
    public abstract ConfigurableFileCollection getSourceFiles();
    /**
     *
     * @return template data
     */
    @Nested
    public abstract TemplateData getTemplateData();

    /**
     *
     * @return output dir
     */
    @OutputDirectory
    public abstract DirectoryProperty getOutputDir();

    // 仅内部使用
    @Internal
    private TemplateEngine getTemplateEngine() {
        if (ReplaceTemplateEngine.class.getSimpleName().equals(getTemplateEngineType().get())) {
            return new ReplaceTemplateEngine(getTemplateData().getVariables().get());
        }
        throw new IllegalArgumentException("不支持的模板引擎");
    }

    /**
     * process template action
     */
    @TaskAction
    public void processTemplate() {
        System.out.println("SourceFiles: " + getSourceFiles());
        System.out.println("TemplateData: " + getTemplateData().getName().getOrNull() + " " + getTemplateData().getVariables().getOrNull());

        System.out.println("\nProcessing...\n");

        getSourceFiles().forEach(file -> {
            File output = new File(getOutputDir().get().getAsFile(), file.getName());
            getTemplateEngine().process(file, output);
            System.out.println("output: " + output);
        });

        System.out.println("\nEnd\n");
    }

    /**
     * set template data
     * @param data template
     */
    public abstract void setTemplateData(TemplateData data);

}
