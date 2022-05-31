package com.lin945.mongoblog.service

import com.lin945.mongoblog.pojo.BlogDO
import com.lin945.mongoblog.pojo.BlogVO
import com.lin945.mongoblog.pojo.Comment
import org.springframework.data.domain.Page

interface BlogService {
    fun getBlogs(page: Int,size:Int): Page<BlogVO>

    fun getComment(id: String):List<Comment>
    fun getBlogById(id: String):BlogVO
}