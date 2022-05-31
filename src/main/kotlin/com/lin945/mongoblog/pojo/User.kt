package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

/**
 * 用户model
 */
@Document("User")
data class UserDO(
    @Id
    var id: ObjectId = ObjectId(),
    var nickname: String,
    val userName: String,
    var password: String,
    var info: String = "",
    val createTime: Date = Date(),
    var updateTime: Date = Date()
) : Serializable

/**
 * 用户登录验证model
 */
data class UserLoginAO(
    var userName: String?,
    var password: String?) : Serializable

/**
 * 用户view模型
 */
data class UserVO(
    var nickname: String,
    val userName: String,
    var info: String = "",
    @JsonFormat(pattern = "yyyy-MM-dd")
    val createTime: Date = Date(),
    @JsonFormat(pattern = "yyyy-MM-dd")
    var updateTime: Date = Date()
)
