package com.slin.study.test

import android.content.Context
import android.util.Log
import com.slin.study.annotation.Initialize

/**
 * MethodB
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/19
 */
class MethodB {

    private val TAG = MethodB::class.java.simpleName

    @Initialize
    fun init(context: Context) {
        Log.d(TAG, "init: $context")
    }

}