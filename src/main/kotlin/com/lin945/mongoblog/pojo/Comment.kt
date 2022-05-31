package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import java.util.*

data class Comment(
    val id: ObjectId = ObjectId(),
    val userName: String,
    val email: String,
    val content: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var createTime: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var updateTime: Date = Date()
)
