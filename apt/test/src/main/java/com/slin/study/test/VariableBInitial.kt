package com.slin.study.test

import android.app.Application
import android.content.Context
import com.slin.study.annotation.Initialize

/**
 * VariableBInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/20
 */
object VariableBInitial {

    @Initialize
    lateinit var context: Application

}