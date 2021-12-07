package com.slin.study.buildsrc.inject;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

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
                .create("downloadExt", DownloadExtension.class);

        target.afterEvaluate(project -> {

            project.getTasks().create("downloadResource", PrintResourceTask.class, printResourceTask -> {
                printResourceTask.setGroup("version");
                printResourceTask.getHostPath().set(downloadExtension.getHostPath());
                printResourceTask.setResources(downloadExtension.getResources());

                printResourceTask.getInputFiles().from(downloadExtension.getInputFiles());

            });
        });
    }

//                NamedDomainObjectContainer<HostPath> resources = multiResourceExtension.getResources();
//                for (int i = 0; i < resources.size(); i++) {
//                    resources.forEach(DownloadPlugin::printResource);
//                }
}
