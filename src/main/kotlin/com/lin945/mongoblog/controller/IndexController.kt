package com.lin945.mongoblog.controller

import com.lin945.mongoblog.dao.AboutDao
import com.lin945.mongoblog.dao.BlogDao
import com.lin945.mongoblog.pojo.AboutDO
import com.lin945.mongoblog.service.AboutService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class IndexController {

    @Autowired
    lateinit var blogDao: BlogDao

    @Autowired
    lateinit var aboutService: AboutService


    /**
     * 博客首页
     */
    @RequestMapping("/")
    fun index(@RequestParam(defaultValue = "0") pageNum: Int = 0): ModelAndView {
        val page = blogDao.findAll(PageRequest.of(pageNum, 5))
        val modelAndView = ModelAndView("index")
        modelAndView.addObject("pages",page)
        return modelAndView
    }

    @RequestMapping("/blog")
    fun blog(): String {
        return "blog"
    }

    @RequestMapping("/about")
    fun about(): AboutDO{

        return aboutService.getAbout()
    }

    @RequestMapping("/archives")
    fun archives(): String {
        return "archives"
    }

    @RequestMapping("/tags")
    fun tags(): String {
        return "tags"
    }

    @RequestMapping("/types")
    fun types(): String {
        return "types"
    }
}