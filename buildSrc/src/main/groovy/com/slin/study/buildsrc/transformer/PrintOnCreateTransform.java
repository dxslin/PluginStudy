package com.slin.study.buildsrc.transformer;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    private void processJarInput(JarInput jarInput, TransformOutputProvider outputProvider) {
        try {
            File[] files = jarInput.getFile().listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.contains("Activity");
                }
            });
            if (files != null) {
                for (File file : files) {
                    String name = file.getName();

                }
            }

            File dest = outputProvider.getContentLocation(jarInput.getName(), jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);

            FileUtils.copyFile(jarInput.getFile(), dest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void processDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        try {
            File dest = outputProvider.getContentLocation(directoryInput.getName(),
                    directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);

            System.out.println(directoryInput.getFile().getName());
            FileUtils.getAllFiles(directoryInput.getFile()).filter(input -> {
                if (input != null) {
                    return input.getName().endsWith("Activity.class");
                }
                return false;
            }).forEach(this::processActivityFile);

            FileUtils.copyDirectory(directoryInput.getFile(), dest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processActivityFile(File source) {
        try {
            ClassReader reader = new ClassReader(new FileInputStream(source));
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
                    if ("onCreate".equals(name)) {
                        return new MethodVisitor(api, mv) {
                            @Override
                            public void visitCode() {
                                // 进入执行OnCreate时调用Log.d(TAG, msg)方法
                                super.visitLdcInsn(reader.getClassName() + "-" + name); // 构建变量 TAG
                                super.visitLdcInsn("Method Enter...");                  // 构建变量 msg
                                super.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);

                                super.visitCode();
                            }

                            @Override
                            public void visitInsn(int opcode) {
                                if(opcode == Opcodes.ATHROW || opcode == Opcodes.RETURN){
                                    // 进入执行OnCreate时调用Log.d(TAG, msg)方法
                                    super.visitLdcInsn(reader.getClassName() + "-" + name); // 构建变量 TAG
                                    super.visitLdcInsn("Method Exit...");                  // 构建变量 msg
                                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                                }

                                super.visitInsn(opcode);
                            }

                        };
                    } else {
                        return mv;
                    }
                }
            };
            reader.accept(visitor, ClassReader.EXPAND_FRAMES);

            FileOutputStream fos = new FileOutputStream(source);
            fos.write(writer.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
