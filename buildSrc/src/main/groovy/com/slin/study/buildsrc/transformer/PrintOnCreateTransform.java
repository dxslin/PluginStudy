package com.slin.study.buildsrc.transformer;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Set;

/**
 * author: slin
 * <p>
 * date: 2021/12/10
 * <p>
 * description:
 */
public class PrintOnCreateTransform extends Transform {

    @Override
    public String getName() {
        return PrintOnCreateTransform.class.getSimpleName();
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return true;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        TransformOutputProvider outputProvider = transformInvocation.getOutputProvider();
        transformInvocation.getInputs().forEach(transformInput -> {
            transformInput.getJarInputs().forEach(jarInput -> {
                processJarInput(jarInput, outputProvider);
            });

            transformInput.getDirectoryInputs().forEach(directoryInput -> {
                processDirectoryInput(directoryInput, outputProvider);
            });

        });

    }

    private void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider){
        File[] files = jarInput.getFile().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.contains("Activity");
            }
        });
    }


    private void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider){


    }

}
