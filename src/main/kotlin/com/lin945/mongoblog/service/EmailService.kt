package com.lin945.mongoblog.service

interface EmailService {
    fun sendLoginMessage(to:String,message:String)
}