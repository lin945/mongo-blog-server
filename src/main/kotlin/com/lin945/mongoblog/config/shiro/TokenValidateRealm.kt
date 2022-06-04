package com.lin945.mongoblog.config.shiro

import com.lin945.mongoblog.utils.getLoginUser
import com.lin945.mongoblog.utils.verify
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.BearerToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.realm.AuthenticatingRealm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
class TokenValidateRealm() : AuthenticatingRealm(matcher) {
    val log : Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * 匹配验证
     */
    object matcher :CredentialsMatcher{
        private val log : Logger = LoggerFactory.getLogger(this.javaClass)
        override fun doCredentialsMatch(token: AuthenticationToken, info: AuthenticationInfo?): Boolean {
            val bearerToken = token as BearerToken
            val tokenString = bearerToken.token
            log.info("tokenMatcher : {}",tokenString)
            log.info("tokenMatcher : {}",info)
            return verify(tokenString)
        }
    }

    override fun getAuthenticationTokenClass(): Class<*> {
        return BearerToken::class.java
    }
    //获取校验信息
    override fun doGetAuthenticationInfo(token: AuthenticationToken?): AuthenticationInfo? {
        val bearerToken = token as BearerToken
        val tokenString = bearerToken.token//token String
        val loginUser = getLoginUser(tokenString)//解析token
        log.info("login User:{}",loginUser)
        return loginUser?.let {
            SimpleAuthenticationInfo(it, tokenString, name)
        }
    }

}