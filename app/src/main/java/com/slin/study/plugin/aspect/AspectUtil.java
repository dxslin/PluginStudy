package com.slin.study.plugin.aspect;
//
//import android.util.Log;
//import android.view.View;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//
//
///**
// * AspectUtil.java
// *
// * @author slin
// * @version 1.0.0
// * @since 2021/12/14
// */
//
//@Aspect
//public class AspectUtil {
//
//    public final static String TAG = AspectUtil.class.getSimpleName();
//
//    @After("execution(public * android.app.Activity.on**(..))")
//    public void activityMethod(JoinPoint joinPoint) throws Throwable {
//        Log.i(TAG, "activityMethod:::" + joinPoint.getSignature());
//    }
//
//
//    @After("execution(* androidx.fragment.app.Fragment.on**(..))")
//    public void fragmentMethod(JoinPoint joinPoint) throws Throwable {
//        Log.i(TAG, "fragmentMethod:::" + joinPoint.getSignature());
//    }
//
//    @After("execution(* com.slin.study.plugin..*Activity.on**(..))")
//    public void myActivityMethod(JoinPoint joinPoint) {
//        Log.i(TAG, "myActivityMethod:::" + joinPoint.getSignature());
//    }
//
//
////    @After("execution(* com.slin.study.plugin..*Activity.setOnLongClickListener(..))")
////    public void  afterLongClick(JoinPoint joinPoint) {
////        Log.d(TAG, "afterLongClick::: " + joinPoint.getSignature());
////    }
//
//    @Around("call(* android.view.View.setOnLongClickListener(android.view.View.OnLongClickListener))")
//    public void  executeLongClick(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.d(TAG, "executeLongClick start: ${joinPoint.signature}");
//        if(joinPoint.getArgs().length > 0) {
//            View.OnLongClickListener argListener = (View.OnLongClickListener) joinPoint.getArgs()[0];
//            View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    Log.d(TAG, "executeLongClick custom listener: " + view);
//                    return argListener.onLongClick(view);
//                }
//            };
//            joinPoint.proceed(new Object[]{longClickListener});
//        } else {
//            joinPoint.proceed();
//        }
//        Log.d(TAG, "executeLongClick end: ${joinPoint.signature}");
//    }
//
//}