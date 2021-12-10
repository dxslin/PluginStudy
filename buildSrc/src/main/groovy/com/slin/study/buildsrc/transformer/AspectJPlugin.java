package com.slin.study.buildsrc.transformer;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * author: slin
 * <p>
 * date: 2021/12/10
 * <p>
 * description:
 */
public class AspectJPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        AppExtension appExtension = project.getExtensions().findByType(AppExtension.class);
        appExtension.registerTransform(new JokerWanTransform(project));
    }


}
