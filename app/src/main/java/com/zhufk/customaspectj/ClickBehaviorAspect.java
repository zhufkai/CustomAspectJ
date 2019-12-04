package com.zhufk.customaspectj;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @ClassName ClickBehaviorAspect
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/4 14:24
 * @Version 1.0
 */
@Aspect
public class ClickBehaviorAspect {
    private final static String TAG = "zhufk -->";

    /*
     * 1、应用中用到了哪些注解，放到当前的切入点进行处理(找到当前需要处理的切入点)
     *  execution，以方法执行时作为切入点，触发Aspect类
     *  * *(..) 可以处理ClickBehavior这个类额所有方法
     */
    @Pointcut("execution(@com.zhufk.customaspectj.ClickBehavior * *(..))")
    public void methodPointcut() {
    }

    /*
     * 2、对切入的点如何处理
     */
    @Around("methodPointcut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取签名方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取方法所属的类名
        String className = methodSignature.getDeclaringType().getSimpleName();
        //获取方法名
        String methodName = methodSignature.getName();
        //获取方法的注解（需要统计用户行为）
        String funName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();

        //统计用户的行为
        long begin = System.currentTimeMillis();
        Log.e(TAG, "ClickBehavior method start >>>");
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        Log.e(TAG, "ClickBehavior method end >>>");
        Log.e(TAG, String.format("统计了：%s功能，在%s类的%s方法，用时%d ms",
                funName, className, methodName, duration));

        return result;
    }
}
