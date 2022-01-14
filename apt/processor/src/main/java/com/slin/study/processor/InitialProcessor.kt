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
    private lateinit var elementUtils:Elements
    private lateinit var filer:Filer


    private lateinit var initiatorCreator:InitiatorCreator

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        messager = processingEnv.messager
        elementUtils = processingEnv.elementUtils
        filer = processingEnv.filer

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

        for (typeElement in typeElementSet){
            log("typeElement: $typeElement")
        }

        if(typeElementSet.isEmpty()){
            return false
        }

        val elementSet = roundEnvironment.getElementsAnnotatedWith(Initialize::class.java)
        for (element in elementSet){
            log("element: $element")
            when(element){
                is TypeElement ->{
                    initiatorCreator.addElement(element)
                    processClassSymbol(element)
                }
                is ExecutableElement ->{

                }
                is VariableElement ->{

                }
            }

        }
        val fileSpec = initiatorCreator.generateCreatorFile()
        fileSpec.writeTo(filer)
        return true
    }

    private fun processClassSymbol(classSymbol:TypeElement) {


    }

    private fun log(msg:CharSequence){
        messager.printMessage(Diagnostic.Kind.WARNING, msg)
    }

}