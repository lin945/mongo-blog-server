package com.lin945.mongoblog.service.impl

import com.lin945.mongoblog.service.EmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl :EmailService {
    @Autowired
    lateinit var mailSender: MailSender
    @Async
    override fun sendLoginMessage(to: String, message: String) {

    }
}