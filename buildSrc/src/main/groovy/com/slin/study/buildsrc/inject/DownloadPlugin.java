package com.slin.study.buildsrc.inject;

import org.gradle.api.Action;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.util.function.Consumer;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public class DownloadPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        DownloadExtension downloadExtension = target.getExtensions()
                .create("downloadExt", DownloadExtension.class, target.getObjects());


        target.afterEvaluate(project -> {

            project.getTasks().create("printlnResources", PrintResourceTask.class, new Action<PrintResourceTask>() {
                @Override
                public void execute(PrintResourceTask printResourceTask) {
                    printResourceTask.getResource().set(downloadExtension.getDownloadResource());
                }
            });
        });
    }

//                NamedDomainObjectContainer<Resource> resources = multiResourceExtension.getResources();
//                for (int i = 0; i < resources.size(); i++) {
//                    resources.forEach(DownloadPlugin::printResource);
//                }
}
