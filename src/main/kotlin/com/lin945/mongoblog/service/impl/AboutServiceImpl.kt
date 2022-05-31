package com.lin945.mongoblog.service.impl

import com.lin945.mongoblog.dao.AboutDao
import com.lin945.mongoblog.dao.BlogDao
import com.lin945.mongoblog.pojo.AboutDO
import com.lin945.mongoblog.service.AboutService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AboutServiceImpl: AboutService {
    @Autowired
    lateinit var aboutDao: AboutDao

    override fun getAbout(): AboutDO {
      return  aboutDao.findAll().getOrNull(0)?:AboutDO(1L,"你好！", Date())
    }

}