package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotEmpty

/**
 * 用户model
 */
@Document("User")
data class UserDO(
    @Id
    var id: ObjectId = ObjectId(),//id
    var nickname: String,//昵称
    @Indexed(unique = true)
    val userName: String,//用户名
    var password: String,//密码
    var info: String = "",//其他信息
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
 * 登录jwt用户
 */
data class LoginUser(val id:String,
                     var userName: String,
                     var nickName: String,
                     var password: String?=null)
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
