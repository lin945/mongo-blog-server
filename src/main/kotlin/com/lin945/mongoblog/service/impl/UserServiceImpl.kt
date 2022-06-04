package com.lin945.mongoblog.service.impl

import com.lin945.mongoblog.dao.UserDao
import com.lin945.mongoblog.pojo.UserDO
import com.lin945.mongoblog.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class UserServiceImpl :UserService {
    @Autowired
    lateinit var userDao: UserDao
    override fun getUserByUserName(username: String): UserDO? {
        return userDao.findUserDOByUserName(username)
    }

}