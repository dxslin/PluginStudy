package com.slin.study.test

import android.util.Log
import com.slin.study.annotation.Initialize

/**
 * MethodInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/12
 */
object MethodInitial  {

    private val TAG = MethodInitial::class.java.simpleName

    @Initialize
    fun methodInitial(){
        Log.d(TAG, "methodInitial: ")
    }

}