package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*

@Document("Blog")
data class BlogDO(
    @Id
    val id: ObjectId = ObjectId(),
    var title: String,
    var content: String,
    var description: String = if (content.length > 50) content.substring(0,50) else content,
    var img: String,
    var published: Boolean = true,
    var views: Long = 0,
    var userId: Long,
    var comments: List<Comment> = emptyList(),
    var createTime: Date,
    var updateTime: Date = Date()
)

public fun BlogDO.toVO()=BlogVO(
    id = id.toString(),
    title = title,
    content = content,
    img = img,
    createTime = createTime,
    updateTime = updateTime,
    views = views,
    commentNumber = comments.size,
    userName = ""
)
data class  BlogVO @JvmOverloads constructor(
    val id: String,
    var title: String,
    var content: String,
    var description: String = if (content.length > 50) content.substring(0,50) else content,
    var img: String = "",
    var published: Boolean = true,
    var views: Long = 0,
    var userName: String,
    var commentNumber: Int = 0,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var createTime: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var updateTime: Date = Date()
) : Serializable