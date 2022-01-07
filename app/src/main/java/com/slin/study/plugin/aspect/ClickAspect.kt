package com.slin.study.plugin.aspect

import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import com.slin.study.plugin.R
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect

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

    /**
     * 使用接口作为切入点拦截，这种方式无法拦截lambda
     */
//    // 定义切入点：View.OnClickListener#onClick()方法
//    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
//    fun executeOnClick() {
//
//    }
//
//    // 定义环绕增强，包装methodViewOnClick()切入点
//    @Around("executeOnClick()")
//    fun callClickMethod(joinPoint: ProceedingJoinPoint) {
//        Log.d(TAG, "callClickMethod: ${joinPoint.signature}")
//        processFastClick(joinPoint)
//    }

    /**
     * 拦截可能为OnClick的lambda表达式，这种方式可能会产生误判，所以不采用
     */
//    // kotlin lambda
//    //void com.slin.study.plugin.MainActivity.onCreate$lambda-0(MainActivity, View)
//    @Around("execution(* com.slin.study.plugin..*lambda*(*, android.view.View))")
//    fun executeOnClickLambdaKotlin(joinPoint: ProceedingJoinPoint) {
//        Log.d(TAG, "executeOnClickLambdaKotlin: ${joinPoint.signature}")
//        processFastClick(joinPoint)
//    }
//
//    // java lambda
//    //void com.slin.study.plugin.activity.AnotherActivity.lambda$onCreate$0(View)
//    @Around("call(* com.slin.study.plugin..*lambda*(android.view.View))")
//    fun executeOnClickLambdaJava(joinPoint: ProceedingJoinPoint) {
//        Log.d(TAG, "executeOnClickLambdaJava: ${joinPoint.signature}")
//        processFastClick(joinPoint)
//    }

    /**
     * 拦截setOnClickListener，修改将传入的listener封装到我们自己防止快速点击事件
     */
    @Around("call(* android.view.View.setOnClickListener(android.view.View.OnClickListener))")
    @Throws(Throwable::class)
    fun callSetOnClickListener(joinPoint: ProceedingJoinPoint) {
        Log.d(TAG, "callSetOnClickListener start: ${joinPoint.signature}")
        if (joinPoint.args.isNotEmpty()) {
            val argListener = joinPoint.args[0] as View.OnClickListener
            val longClickListener = View.OnClickListener { view ->
                Log.d(TAG, "callSetOnClickListener custom listener: $view")
                if(!isFastClick(view)){
                    argListener.onClick(view)
                }
            }
            joinPoint.proceed(arrayOf<Any>(longClickListener))
        } else {
            joinPoint.proceed()
        }
        Log.d(TAG, "callSetOnClickListener end: ${joinPoint.signature}")
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