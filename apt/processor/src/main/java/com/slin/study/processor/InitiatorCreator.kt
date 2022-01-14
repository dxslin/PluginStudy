package com.slin.study.processor

import com.slin.study.annotation.Initial
import com.squareup.kotlinpoet.*
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.TypeElement

/**
 * InitiatorCreator
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/12
 */
class InitiatorCreator(private val processingEnv: ProcessingEnvironment) {

    private val mPackageName: String = "com.slin.apt.generated"
    private val mClassName: String = processingEnv.locale.variant + "Initiator"

    private val elementList: MutableList<TypeElement> = mutableListOf()

    fun addElement(element: TypeElement) {
        elementList.add(element)
    }

    fun generateCreatorFile(): FileSpec {
        val fileSpecBuilder = FileSpec.builder(mPackageName, mClassName)
        elementList.map {
            it.asClassName()
        }.forEach {
            fileSpecBuilder.addImport(it.packageName, it.simpleName)
        }
        val fileSpec = fileSpecBuilder.addImport("android.content", "Context")
            .addType(genClassType())
            .build()
        fileSpec.writeTo(System.out)
        return fileSpec
    }

    private fun genClassType(): TypeSpec {
        return TypeSpec.objectBuilder(mClassName)
            .addFunction(
                FunSpec.builder("initial")
                    .addParameter("context", ClassName("android.content", "Context"))
                    .addCode(genInitCode())
                    .build()
            )
            .build()
    }

    private fun genInitCode(): CodeBlock {
        val codeBlock = CodeBlock.builder()
        for (element in elementList) {
            codeBlock.add("${element.asClassName().simpleName}().initial()\n")
        }
        return codeBlock.build()
    }

}