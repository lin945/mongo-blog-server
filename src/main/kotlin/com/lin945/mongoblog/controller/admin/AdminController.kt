package com.lin945.mongoblog.controller.admin

import com.lin945.mongoblog.pojo.*
import com.lin945.mongoblog.service.BlogService
import com.lin945.mongoblog.utils.generateToken
import com.lin945.mongoblog.utils.getLoginUser
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/admin")
class AdminController {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var blogService: BlogService
    /**
     * 登录接口
     */
    @PostMapping("/login")
    fun login(@RequestBody user: UserLoginAO): Result<String> {
       return user.takeIf {
            it.userName != null && it.password != null
        }?.let {
            val token = UsernamePasswordToken(it.userName, it.password)
            val subject = SecurityUtils.getSubject()!!
            subject.login(token)
            val principal = getLoginUser()
            Result.ok(generateToken(principal))
        } ?: throw RuntimeException(CodeConfig.PARAM_ERROR.message)
    }


    /**
     * 添加博客
     */
    @PostMapping("/blog/add")
    fun addBlog(@RequestBody blog:BlogCreateAO): Result<Unit> {
        val loginUser = getLoginUser()
        val blogDO = BlogDO(
            title = blog.title,
            content = blog.content,
            published = true,
            createTime = Date(),
            img = blog.img,
            userId = loginUser.id,
            description = blog.description
        )
        blogService.save(blogDO)
        return Result.ok()
    }



    @RequestMapping("/test")
    fun test(): Result<String> {
        val principal = getLoginUser()
        return Result.ok(principal.toString())
    }
}