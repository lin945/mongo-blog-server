package com.lin945.mongoblog.dao

import com.lin945.mongoblog.pojo.AboutDO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface AboutDao : MongoRepository<AboutDO, ObjectId> {

}