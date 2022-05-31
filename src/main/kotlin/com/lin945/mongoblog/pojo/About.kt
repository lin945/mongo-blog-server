package com.lin945.mongoblog.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
@Document("about")
data class AboutDO(
    @Id
    val id:Long=1L,
    var content: String,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var createTime: Date,
    @JsonFormat(pattern = "yyyy-MM-dd")
    var updateTime: Date = Date()
)
