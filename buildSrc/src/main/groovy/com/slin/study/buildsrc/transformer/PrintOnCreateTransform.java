package com.slin.study.buildsrc.transformer;

import com.android.build.api.instrumentation.AsmClassVisitorFactory;
import com.android.build.api.instrumentation.ClassContext;
import com.android.build.api.instrumentation.ClassData;
import com.android.build.api.instrumentation.InstrumentationParameters;

import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassVisitor;

/**
 * author: slin
 * <p>
 * date: 2021/12/10
 * <p>
 * description:
 * https://blog.csdn.net/chuyouyinghe/article/details/125274309
 */
public abstract class PrintOnCreateTransform implements AsmClassVisitorFactory<InstrumentationParameters.None> {


    @NotNull
    @Override
    public ClassVisitor createClassVisitor(@NotNull ClassContext classContext, @NotNull ClassVisitor classVisitor) {
        return new PrintOnCreateVisitor(classContext, classVisitor);
    }

    @Override
    public boolean isInstrumentable(@NotNull ClassData classData) {
        return true;
    }
}
