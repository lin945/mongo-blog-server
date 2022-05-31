package com.lin945.mongoblog.config

import org.springframework.core.annotation.AnnotationUtils
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler {
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

}