package com.slin.study.buildsrc.transformer;

import com.android.build.api.instrumentation.ClassContext;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PrintOnCreateVisitor extends ClassVisitor {

    private final static int CLASS_API = Opcodes.ASM7;

    private ClassContext classContext;

    public PrintOnCreateVisitor(ClassContext classContext, ClassVisitor nextVisitor) {
        super(CLASS_API, nextVisitor);
        this.classContext = classContext;
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if ("onCreate".equals(name)) {
            return new MethodVisitor(api, mv) {
                @Override
                public void visitCode() {

                    // 进入执行OnCreate时调用Log.d(TAG, msg)方法
                    super.visitLdcInsn(classContext.getCurrentClassData().getClassName() + "-" + name); // 构建变量 TAG
                    super.visitLdcInsn("Method Enter...");                  // 构建变量 msg
                    super.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "d", "(Ljava/lang/String;Ljava/lang/String;)I", false);

                    super.visitCode();
                }

                @Override
                public void visitInsn(int opcode) {
                    if (opcode == Opcodes.ATHROW || opcode == Opcodes.RETURN) {
                        // 进入执行OnCreate时调用Log.d(TAG, msg)方法
                        super.visitLdcInsn(classContext.getCurrentClassData().getClassName() + "-" + name); // 构建变量 TAG
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
}
