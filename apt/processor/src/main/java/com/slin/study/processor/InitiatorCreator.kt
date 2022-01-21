package com.slin.study.processor

import com.slin.study.annotation.Initial
import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

/**
 * InitiatorCreator
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/12
 */
class InitiatorCreator(private val env: ProcessingEnvironment) {

    private val mPackageName: String = "com.slin.apt.generated"
    private val mClassName: String = "Initiator"

    private val elementList: MutableList<ElementModel> = mutableListOf()

    fun addElement(element: ElementModel) {
        elementList.add(element)
    }

    fun generate() {
        val fileSpecBuilder = FileSpec.builder(mPackageName, mClassName)
        elementList.forEach {
            fileSpecBuilder.addImport(it.packageName, it.className)
        }
        val fileSpec = fileSpecBuilder.addImport("android.app", "Application")
            .addType(initiatorType())
            .build()
        fileSpec.writeTo(System.out)
        fileSpec.writeTo(env.filer)
    }

    private fun initiatorType(): TypeSpec {
        return TypeSpec.objectBuilder(mClassName)
            .addFunction(
                FunSpec.builder("initial")
                    .addParameter("context", ClassName("android.app", "Application"))
                    .addCode(initCode())
                    .build()
            )
            .build()
    }

    private fun initCode(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        for (element in elementList) {
            when {
                element.methodName.isEmpty() -> {
                    codeBlock.add("${element.className}${if (element.createInstance) "(${if (element.hasContext) "context" else ""})" else ""}\n")
                }
                element.isVariable -> {
                    codeBlock.add("${element.className}${if (element.createInstance) "()" else ""}.${element.methodName} = context\n")
                }
                else -> {
                    codeBlock.add("${element.className}${if (element.createInstance) "()" else ""}.${element.methodName}(${if (element.hasContext) "context" else ""})\n")
                }
            }
        }
        return codeBlock.build()
    }

}