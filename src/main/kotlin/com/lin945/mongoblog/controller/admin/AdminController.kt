package com.lin945.mongoblog.controller.admin

import com.lin945.mongoblog.pojo.CodeConfig
import com.lin945.mongoblog.pojo.LoginUser
import com.lin945.mongoblog.pojo.Result
import com.lin945.mongoblog.pojo.UserLoginAO
import com.lin945.mongoblog.utils.generateToken
import com.lin945.mongoblog.utils.getLoginUser
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)


    @PostMapping("/login")
    public fun login(@RequestBody user: UserLoginAO): Result<String> {
       return user.takeIf {
            it.userName != null && it.password != null
        }?.let {
            val token = UsernamePasswordToken(it.userName, it.password)
            val subject = SecurityUtils.getSubject()!!
            subject.login(token)
            val principal = SecurityUtils.getSubject().principal as LoginUser
            log.info(principal.toString())
            Result.ok(generateToken(principal))
        } ?: throw RuntimeException(CodeConfig.PARAM_ERROR.message)
    }


    @RequestMapping("/test")
    fun test(): Result<String> {
        val principal = SecurityUtils.getSubject().principal as LoginUser;
        return Result.ok(principal.toString())
    }
}