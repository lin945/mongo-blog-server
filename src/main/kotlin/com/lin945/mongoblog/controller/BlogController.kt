package com.lin945.mongoblog.controller

import com.lin945.mongoblog.pojo.BlogVO
import com.lin945.mongoblog.pojo.Comment
import com.lin945.mongoblog.pojo.Result
import com.lin945.mongoblog.service.BlogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
open class BlogController {

    @Autowired
    lateinit var blogService:BlogService


    @RequestMapping("/comment/{id}")
    fun blog(@PathVariable("id") id: String):Result<List<Comment>>  {
       return Result.ok(blogService.getComment(id))
    }

    /**
     * 获取所有blog
     */
    @RequestMapping("/blog/all")
    fun blogs(@RequestParam("page") page:Int=0, @RequestParam("size") size:Int?): Result<Page<BlogVO>> {
        return Result.ok(blogService.getBlogs(page,size?:5))
    }

    /**
     * 单个文章
     */
    @RequestMapping("/blog/")
    fun blogById(@RequestParam("id") id: String): Result<BlogVO> {
        return  Result.ok(blogService.getBlogById(id))
    }


}