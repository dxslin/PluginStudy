package com.slin.study.buildsrc.site;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * author: slin
 * <p>
 * date: 2021/12/3
 * <p>
 * description:
 */
public class SitePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        SiteExtension siteExtension = project.getExtensions().create("site", SiteExtension.class);


        project.getTasks().create("site", task -> {
            task.setGroup("version");
            task.doLast(task1 -> {
                System.out.println("siteExtension: ");
                System.out.println(siteExtension.getOutputDir().getAsFile().get());
                System.out.println(siteExtension.getCustomData().getVcsUrl().get());
                System.out.println(siteExtension.getCustomData().getWebsiteUrl().get());
            });
        });

    }


}
