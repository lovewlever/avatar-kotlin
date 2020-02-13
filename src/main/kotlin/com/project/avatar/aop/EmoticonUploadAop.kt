package com.project.avatar.aop

import com.project.avatar.common.ResultCommon
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.springframework.stereotype.Component

@Component
@Aspect
class EmoticonUploadAop {

    @Pointcut("execution(public * com.project.avatar.controller.EmoticonPackageController.uploadEmoticonPackage(..))")
    fun onPointcut() {}


    @Before("onPointcut()")
    fun onBefore() {
        println("前置通知")
    }


    @After("onPointcut()")
    fun onAfter() {
        println("后置通知")
    }


    @Around("onPointcut()")
    fun onAround(proceedingJoinPoint: ProceedingJoinPoint):Any{
        println("前置环绕通知")
        val args = proceedingJoinPoint.args
        args.forEach {
            print(it.toString())
        }
        val proceed = proceedingJoinPoint.proceed()
        println("后置环绕通知")
        return ResultCommon.generateError<String>(msg = "权限不足")
    }
}