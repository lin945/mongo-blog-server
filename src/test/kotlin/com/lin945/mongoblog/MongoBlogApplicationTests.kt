package com.lin945.mongoblog

import com.lin945.mongoblog.dao.AboutDao
import com.lin945.mongoblog.dao.BlogDao
import com.lin945.mongoblog.dao.UserDao
import com.lin945.mongoblog.pojo.*
import com.lin945.mongoblog.service.AboutService
import com.lin945.mongoblog.service.BlogService
import com.lin945.mongoblog.service.UserService
import com.lin945.mongoblog.utils.generateToken
import com.lin945.mongoblog.utils.getLoginUser
import com.lin945.mongoblog.utils.verify
import org.apache.shiro.crypto.hash.Md5Hash
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
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

    @Autowired
    lateinit var userService: UserService

    @Test
    fun contextLoads() {
        userDao.insert(UserDO(nickname = "hello", userName = "username", password = "password"))
        println(userDao.findAll())
    }

    @Test
    fun addBlog() {
        for (i in 1..15) {
            blogDao.insert(
                BlogDO(
                    title = "${i * 2}",
                    comments = listOf(
                        Comment(
                            userName = "233",
                            email = "1@qqq.com",
                            content = "asdfasfasfs",
                            createTime = Date()
                        )
                    ),
                    content = "${i * 2}正文啊实打实的发生",
                    description = "小小的描述",
                    img = "https://bilibili.asia/banner01.jpg",
                    createTime = Date(),
                    userId = "1"
                )
            )
        }

        println(blogDao.findAll())
    }

//    @Test
//    fun MarkdownTest() {
//        val src = "Some *Markdown*"
//        val flavour = CommonMarkFlavourDescriptor()
//        val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(src)
//        val html = HtmlGenerator(src, parsedTree, flavour).generateHtml()
//        println(html)
//    }
    @Test
    fun addUser() {
        val user = UserDO(nickname = "林唯心", password = "123456", userName = "admin")
        userDao.save(user)
        println(userDao.findAll())
    }

    @Test
    fun findUser(){
        println(userService.getUserByUserName("admin"))
        println(userService.getUserByUserName("aaa"))
    }


    @Test
    fun findTest() {
//        Example.of(BlogDO(),b)
//        blogDao.findAll(Ex,PageRequest.of(0,10))
        println(blogService.getComment(""))
    }

    @Autowired
    lateinit var aboutDao: AboutDao


    @Test
    fun addBlolog() {
        println(
            blogService.save(
                BlogDO(
                    title = "这是一片测试",
                    comments = listOf(
                        Comment(
                            userName = "233",
                            email = "1@qqq.com",
                            content = "asdfasfasfs",
                            createTime = Date()
                        )
                    ),
                    content = "正文啊实打实的发生",
                    description = "小小的描述",
                    createTime = Date(),
                    userId = "1"
                )
            )
        )
    }
    @Test
    fun preintPass() {
        println(Md5Hash("123456", null, 2).toString())
    }

    @Test
    fun regiserUser() {
        userDao.save(UserDO(nickname = "林唯心", userName = "root", info = "哈哈哈哈哈哈哈哈哈", password = "123456"))

    }

    @Test
    fun cacheTest() {
        println(blogService.getBlogs(0, 5))
        println(blogService.getBlogs(0, 5))
    }

    @Test
    fun createUser() {
        println(verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMSIsInVzZXJOYW1lIjoidXNlck5hbWUiLCJuaWNrTmFtZSI6Im5pY2tOYW1lIiwiaWF0IjoxNjU0MzkxMTkwLCJleHAiOjE2NTQ5OTU5OTB9.SubR3PdnV3I_S4NAUDB7pl_tmJPh-zoRU3y6kAUq67c"))
        val generateToken = generateToken(LoginUser("11", "userName", "nickName", password = "aaa"))
        println(generateToken)
        println(getLoginUser(generateToken))
    }
    @Test
    fun insertAbout() {
        aboutDao.insert(AboutDO(1, "你好世界！", Date()))
    }

    @Autowired
    lateinit var aboutService: AboutService

    @Test
    fun getAbout() {
        println(aboutService.getAbout())
    }
}
