package com.slin.study.annotation

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class Initialize {

}

/**
 * 初始化接口
 */
interface Initial {

    fun initial()

}