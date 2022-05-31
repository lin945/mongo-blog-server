package com.lin945.mongoblog.dao

import com.lin945.mongoblog.pojo.UserDO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDao: MongoRepository<UserDO, ObjectId> {

}