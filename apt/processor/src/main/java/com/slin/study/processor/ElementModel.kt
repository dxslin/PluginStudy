package com.slin.study.processor

import com.slin.study.annotation.Initial
import com.squareup.kotlinpoet.asClassName
import java.util.*
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.SimpleElementVisitor6
import javax.lang.model.util.SimpleElementVisitor8

/**
 * ElementModel
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/18
 */
data class ElementModel(
    val packageName: String,        // 包名
    val className: String,          // 类名
    val methodName: String,         // 方法名
    val hasContext: Boolean = false,    // 是否有context参数
    val createInstance: Boolean,        // 是否需要创建实例，object class不需要创建
    val isVariable: Boolean = false,     // 是否成员变量
)

const val CONTEXT = "android.content.Context"
const val APPLICATION = "android.app.Application"

fun TypeElement.asElementModel(): ElementModel {
    val className = asClassName()
    var methodName = ""
    var createInstance = true
    //判断是否继承了 `Initial` 接口，如果继承了就调用其 `initial` 方法
    for (itf in interfaces) {
        if ("com.slin.study.annotation.Initial" == itf.toString()) {
            methodName = "initial"
            break
        }
    }

    var hasContext = false
    for (enclosedElement in enclosedElements) {
        // 构造函数名字为<init>
        if (methodName.isEmpty() && enclosedElement is ExecutableElement &&
            enclosedElement.simpleName.contentEquals("<init>")
        ) {
            hasContext = enclosedElement.hasContext()
        } else if (enclosedElement is VariableElement &&
            enclosedElement.simpleName.contentEquals("INSTANCE")) {
            createInstance = false
        }
    }

    return ElementModel(
        packageName = className.packageName,
        className = className.simpleName,
        methodName = methodName,
        hasContext = hasContext,
        createInstance = createInstance
    )
}

fun ExecutableElement.asElementModel(): ElementModel {
    if (enclosingElement !is TypeElement) {
        throw IllegalArgumentException("Methods of this type are not supported")
    }
    val typeElement = enclosingElement as TypeElement
    val className = typeElement.asClassName()
    var createInstance = true
    for (enclosedElement in typeElement.enclosedElements) {
        // object class 包含一个INSTANCE对象
        if (enclosedElement is VariableElement && enclosedElement.simpleName.contentEquals("INSTANCE")) {
            createInstance = false
            break
        }
    }

    return ElementModel(
        packageName = className.packageName,
        className = className.simpleName,
        methodName = simpleName.toString(),
        hasContext = hasContext(),
        createInstance = createInstance
    )

}

fun VariableElement.asElementModel(): ElementModel {
    if (enclosingElement !is TypeElement) {
        throw IllegalArgumentException("Variable of this type are not supported")
    }
    if (!isContext()) {
        throw IllegalArgumentException("Only support '$CONTEXT' value")
    }

    val typeElement = enclosingElement as TypeElement
    val className = typeElement.asClassName()
    var createInstance = true
    for (enclosedElement in typeElement.enclosedElements) {
        // Kotlin中object类有一个INSTANCE对象
        if (enclosedElement is VariableElement && enclosedElement.simpleName.contentEquals("INSTANCE")) {
            createInstance = false
            break
        }
    }

    return ElementModel(
        packageName = className.packageName,
        className = className.simpleName,
        methodName = simpleName.toString(),
        hasContext = true,
        createInstance = createInstance,
        isVariable = true
    )


}

private fun ExecutableElement.hasContext(): Boolean {
    if (parameters.size > 1) {
        throw IllegalArgumentException("Only support no-args function or 1-arg function like fun(${CONTEXT})")
    }
    return parameters.size == 1 && parameters[0].isContext()
}

private fun VariableElement.isContext(): Boolean {
    return asType().toString() == CONTEXT || asType().toString() == APPLICATION
}
