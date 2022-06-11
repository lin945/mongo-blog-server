package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotEmpty

@Document("Blog")
data class BlogDO @JvmOverloads constructor(
    @Id
    val id: ObjectId = ObjectId(),
    var title: String,
    var content: String,
    var description: String,
    var img: String?=null,
    var published: Boolean = true,
    var views: Long = 0,
    var userId: String,
    var comments: List<Comment> = emptyList(),
    var createTime: Date,
    var updateTime: Date = Date()
)

fun BlogDO.toVO()=BlogVO(
    id = id.toString(),
    title = title,
    content = content,
    img = img,
    createTime = createTime,
    updateTime = updateTime,
    views = views,
    commentNumber = comments.size,
    userName = "",
    description = description
)
data class BlogCreateAO @JvmOverloads constructor(var title: String, var content: String, var img:String="",var description: String=if (content.length > 50) content.substring(0,50) else content )

data class  BlogVO @JvmOverloads constructor(
    val id: String,
    var title: String,
    var content: String,
    var description: String,
    var img: String? = null,
    var published: Boolean = true,
    var views: Long = 0,
    var userName: String,
    var commentNumber: Int = 0,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var createTime: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var updateTime: Date = Date()
) : Serializable