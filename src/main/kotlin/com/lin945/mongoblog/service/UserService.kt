package com.lin945.mongoblog.service

import com.lin945.mongoblog.pojo.UserDO

interface UserService {
    fun getUserByUserName(username:String):UserDO?
}