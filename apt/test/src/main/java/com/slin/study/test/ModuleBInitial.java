package com.slin.study.test;

import android.util.Log;

import com.slin.study.annotation.Initial;
import com.slin.study.annotation.Initialize;

/**
 * ModuleBInitial
 *
 * @author slin
 * @version 1.0.0
 * @since 2022/1/11
 */
@Initialize
public class ModuleBInitial implements Initial {

    private final static String TAG = ModuleBInitial.class.getSimpleName();

    @Override
    public void initial() {
        Log.d(TAG, "initial: ");

    }

}
