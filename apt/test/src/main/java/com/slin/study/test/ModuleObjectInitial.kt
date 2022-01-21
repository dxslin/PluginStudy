package com.slin.study.test

import android.util.Log
import com.slin.study.annotation.Initial
import com.slin.study.annotation.Initialize

/**
 * ModuleObjectInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/21
 */
@Initialize
object ModuleObjectInitial : Initial {

    private val TAG = ModuleObjectInitial::class.java.simpleName

    override fun initial() {
        Log.d(TAG, "initial: ")
    }
}