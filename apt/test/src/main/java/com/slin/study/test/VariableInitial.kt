package com.slin.study.test

import android.content.Context
import com.slin.study.annotation.Initialize

/**
 * VariableInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/12
 */
class VariableInitial {
    private val TAG = VariableInitial::class.java.simpleName

    @Initialize
    var context:Context? = null


}