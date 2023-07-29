package com.slin.study.buildsrc.transformer;

import com.android.build.api.extension.impl.VariantSelectorImpl;
import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.ApplicationAndroidComponentsExtension;

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
        ApplicationAndroidComponentsExtension appExtension =
                project.getExtensions().findByType(ApplicationAndroidComponentsExtension.class);

        appExtension.onVariants(new VariantSelectorImpl(), variant -> {
            variant.getInstrumentation().transformClassesWith(
                    PrintOnCreateTransform.class, InstrumentationScope.PROJECT, none -> null);
            variant.getInstrumentation().setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_INSTRUMENTED_METHODS);


        });

    }


}
