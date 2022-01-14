package com.slin.study.plugin.apt

import android.util.Log
import com.slin.study.annotation.Initial
import com.slin.study.annotation.Initialize

/**
 * ModuleAInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/11
 */
@Initialize
class ModuleAInitial :Initial {

    private val TAG = ModuleAInitial::class.java.simpleName

    @Initialize
    private var name = ""


    override fun initial() {
        Log.d(TAG, "initial: ")

    }

}