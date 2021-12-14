package com.slin.study.plugin.aspect

import android.util.Log
import android.view.View
import com.slin.study.plugin.R
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * ClickAspect
 *
 * 使用AspectJ来处理快速点击
 *
 * @author slin
 * @version 1.0.0
 * @since 2021/12/14
 */

@Aspect
class ClickAspect {
        private val TAG = ClickAspect::class.java.simpleName
//    private val TAG = AspectUtil.TAG

    // 定义切入点：View.OnClickListener#onClick()方法
    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    fun executeOnClick() {

    }

    // 定义环绕增强，包装methodViewOnClick()切入点
    @Around("executeOnClick()")
    fun callClickMethod(joinPoint: ProceedingJoinPoint) {
        Log.d(TAG, "callClickMethod: ${joinPoint.signature}")
        processFastClick(joinPoint)
    }

    // 下面是lambda方式，但是这种方式可能会误判非OnClick的lambda
    //void com.slin.study.plugin.MainActivity.onCreate$lambda-0(MainActivity, View)
    @Around("execution(* com.slin.study.plugin..*lambda*(*, android.view.View))")
    fun executeOnClickLambdaKotlin(joinPoint: ProceedingJoinPoint) {
        Log.d(TAG, "executeOnClickLambdaKotlin: ${joinPoint.signature}")
        processFastClick(joinPoint)
    }

    //void com.slin.study.plugin.activity.AnotherActivity.lambda$onCreate$0(View)
    @Around("call(* com.slin.study.plugin..*lambda*(android.view.View))")
    fun executeOnClickLambdaJava(joinPoint: ProceedingJoinPoint) {
        Log.d(TAG, "executeOnClickLambdaJava: ${joinPoint.signature}")
        processFastClick(joinPoint)
    }



    private fun processFastClick(joinPoint: ProceedingJoinPoint) {
        val start = System.currentTimeMillis()
        val arg0 = joinPoint.args[0]
        if (arg0 !is View || !isFastClick(arg0)) {
            joinPoint.proceed()
        }
        val end = System.currentTimeMillis()
        Log.d(TAG, "processFastClick::: ${joinPoint.signature} usage time: ${end - start}ms")
    }


    companion object{

        // 判断是否是快速点击
        fun isFastClick(view: View): Boolean {
            val lastClickTime = view.getTag(R.id.view_click_time)
            if (lastClickTime is Long && System.currentTimeMillis() - lastClickTime < 1000) {
                return true
            }
            view.setTag(R.id.view_click_time, System.currentTimeMillis())
            return false
        }
    }

}