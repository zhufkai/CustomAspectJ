package com.zhufk.customaspectj;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @ClassName ClickBehaviorAspect
 * @Description TODO
 * @Author zhufk
 * @Date 2019/12/4 14:24
 * @Version 1.0
 */
@Aspect
public class LoginCheckAspect {
    private final static String TAG = "zhufk -->";

    /*
     * 1、应用中用到了哪些注解，放到当前的切入点进行处理(找到当前需要处理的切入点)
     *  execution，以方法执行时作为切入点，触发Aspect类
     *  * *(..) 可以处理ClickBehavior这个类额所有方法
     */
    @Pointcut("execution(@com.zhufk.customaspectj.LoginCheck * *(..))")
    public void methodPointcut() {
    }

    /*
     * 2、对切入的点如何处理
     */
    @Around("methodPointcut()")
    public Object joinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Context context = (Context) joinPoint.getThis();
        if (false) {
            Log.e(TAG, "检测到已登录");
            return joinPoint.proceed();
        } else {
            Log.e(TAG, "检测到未登录");
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context,LoginActivity.class));
            return null;//使方法不在往下执行
        }
    }
}
