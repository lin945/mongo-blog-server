package com.lin945.mongoblog

import com.lin945.mongoblog.dao.AboutDao
import com.lin945.mongoblog.dao.BlogDao
import com.lin945.mongoblog.dao.UserDao
import com.lin945.mongoblog.pojo.AboutDO
import com.lin945.mongoblog.pojo.BlogDO
import com.lin945.mongoblog.pojo.Comment
import com.lin945.mongoblog.pojo.UserDO
import com.lin945.mongoblog.service.AboutService
import com.lin945.mongoblog.service.BlogService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

@SpringBootTest
class MongoBlogApplicationTests {
    @Autowired
    lateinit var tempalete: MongoTemplate
    @Autowired
    lateinit var userDao: UserDao
    @Autowired
    lateinit var blogDao: BlogDao
    @Autowired
    lateinit var blogService: BlogService
    @Test
    fun contextLoads() {
        userDao.insert(UserDO(nickname = "hello",userName = "username",password = "password"))
        println(userDao.findAll())
    }

    @Test
    fun addBlog() {
        for (i in 1..15) {
            blogDao.insert(BlogDO(title = "${i * 2}",
                comments = listOf(Comment(userName =  "233",email =  "1@qqq.com",content =  "asdfasfasfs",createTime = Date())),
                content = "${i * 2}正文啊实打实的发生",description = "小小的描述",img = "https://bilibili.asia/banner01.jpg",createTime = Date(),
                userId = 1))
        }

        println(blogDao.findAll())
    }

    @Test
    fun findTest() {
//        Example.of(BlogDO(),b)
//        blogDao.findAll(Ex,PageRequest.of(0,10))
        println(blogService.getComment(""))
    }
    @Autowired
    lateinit var aboutDao:AboutDao


    @Test
    fun insertAbout() {
        aboutDao.insert(AboutDO(1,"你好世界！", Date()))
    }
    @Autowired
    lateinit var aboutService: AboutService
    @Test
    fun getAbout() {
        println(aboutService.getAbout())
    }
}
