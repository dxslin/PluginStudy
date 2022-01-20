package com.slin.study.plugin

import android.app.Application
import com.slin.apt.generated.Initiator

/**
 * AppApplication
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/20
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Initiator.initial(this)

    }

}