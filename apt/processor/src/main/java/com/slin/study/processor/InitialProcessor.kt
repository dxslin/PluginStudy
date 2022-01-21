package com.slin.study.processor

import com.google.auto.service.AutoService
import com.google.common.collect.ImmutableSet
import com.slin.study.annotation.Initialize
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic

@AutoService(Processor::class)
class InitialProcessor: AbstractProcessor() {

    private lateinit var messager:Messager

    // 初始化代码生成器
    private lateinit var initiatorCreator:InitiatorCreator

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        messager = processingEnv.messager

        initiatorCreator = InitiatorCreator(processingEnv)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return ImmutableSet.of(Initialize::class.java.name)
    }

    override fun process(typeElementSet: MutableSet<out TypeElement>, roundEnvironment: RoundEnvironment): Boolean {

        log("InitialProcessor process: $typeElementSet $roundEnvironment")

        // 如果前面的processor发生错误，或者获取到的注解为空，则直接返回不处理
        if(roundEnvironment.errorRaised() || roundEnvironment.processingOver() || typeElementSet.isEmpty()){
            return false
        }

        for (typeElement in typeElementSet){
            log("typeElement: $typeElement")
            if(typeElement.qualifiedName.contentEquals(Initialize::class.java.name)){
//                val elementSet = roundEnvironment.getElementsAnnotatedWith(Initialize::class.java)
                val elementSet = roundEnvironment.getElementsAnnotatedWith(typeElement)
                for (element in elementSet){
                    log("element: $element")
                    when(element){
                        is TypeElement ->{
                            initiatorCreator.addElement(element.asElementModel())
                        }
                        is ExecutableElement ->{
                            initiatorCreator.addElement(element.asElementModel())
                        }
                        is VariableElement ->{
                            initiatorCreator.addElement(element.asElementModel())
                        }
                    }
                }
                // 生成kotlin文件
                initiatorCreator.generate()
                return true
            }
        }
        return false
    }

    private fun log(msg:CharSequence){
        messager.printMessage(Diagnostic.Kind.WARNING, msg)
    }

}