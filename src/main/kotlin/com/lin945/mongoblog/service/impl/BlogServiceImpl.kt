package com.lin945.mongoblog.service.impl

import com.lin945.mongoblog.dao.BlogDao
import com.lin945.mongoblog.pojo.BlogDO
import com.lin945.mongoblog.pojo.BlogVO
import com.lin945.mongoblog.pojo.Comment
import com.lin945.mongoblog.pojo.toVO
import com.lin945.mongoblog.service.BlogService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class BlogServiceImpl:BlogService {
    @Autowired
    lateinit var blogDao: BlogDao
    @Autowired
    lateinit var tempalete:MongoTemplate

    /**
     * 获取所有blogs
     */
    override fun getBlogs(page: Int,size:Int): Page<BlogVO> {
        return blogDao.findAll(PageRequest.of(page,size)).map(BlogDO::toVO)
    }
    override fun getComment(id: String):List<Comment> {
        val findById = blogDao.findById(ObjectId(id)).get()
        return findById.comments
    }

    override fun getBlogById(id: String): BlogVO {
      return  blogDao.findById(ObjectId(id)).get().toVO()
    }
}