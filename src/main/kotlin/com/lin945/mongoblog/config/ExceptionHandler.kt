package com.lin945.mongoblog.config

import com.lin945.mongoblog.pojo.CodeConfig
import com.lin945.mongoblog.pojo.Result
import org.apache.shiro.authc.IncorrectCredentialsException
import org.apache.shiro.authc.UnknownAccountException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler {
    val log : Logger = LoggerFactory.getLogger(this.javaClass)
//    @ExceptionHandler(Exception::class)
//    @Throws(Exception::class)
//    fun exceptionHandler(request: HttpServletRequest, e: Exception): ModelAndView {
//        if (AnnotationUtils.findAnnotation(
//                e.javaClass,
//                ResponseStatus::class.java
//            ) != null
//        ) {
//            throw e
//        }
//        val modelAndView = ModelAndView()
//        modelAndView.addObject("url", request.getRequestURL().toString())
//        modelAndView.addObject("exception", e)
//        modelAndView.setViewName("error/error")
//        return modelAndView
//    }
    @ExceptionHandler(Exception::class)
    @ResponseBody
    fun handlerAll(e: Exception): Result<Unit> {
        log.info(e.toString())
        val message = e.localizedMessage
        return Result.fail(CodeConfig.ERROR).apply {
            this.message=message
        }
    }

    @ExceptionHandler(IncorrectCredentialsException::class)
    @ResponseBody
    fun handlerAll(e: IncorrectCredentialsException): Result<Unit> {
        val message = e.localizedMessage
        log.warn(message)
        return Result.fail(CodeConfig.ERROR_PASSWORD).apply {
            this.message="傻逼密码错了！"
        }
    }


    @ExceptionHandler(UnknownAccountException::class)
    @ResponseBody
    fun handlerAll(e: UnknownAccountException): Result<Unit> {
        val message = e.localizedMessage
        log.warn(message)
        return Result.fail(CodeConfig.ERROR_NAME)
    }
}