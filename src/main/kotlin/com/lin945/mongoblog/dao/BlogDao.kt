package com.lin945.mongoblog.dao

import com.lin945.mongoblog.pojo.BlogDO
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface BlogDao: MongoRepository<BlogDO, ObjectId> {



}