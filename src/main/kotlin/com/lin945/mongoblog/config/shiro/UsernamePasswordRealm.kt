package com.lin945.mongoblog.config.shiro

import com.lin945.mongoblog.pojo.LoginUser
import com.lin945.mongoblog.service.UserService
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.crypto.hash.Md5Hash
import org.apache.shiro.realm.AuthenticatingRealm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
 * 用户名密码验证
 */
@Component
open class UsernamePasswordRealm() : AuthenticatingRealm() {
    val log = LoggerFactory.getLogger(this::class.java)



    @Autowired
    lateinit var userService: UserService

    init {
        //密码保存策略一致，2次md5加密
        val hashedCredentialsMatcher = HashedCredentialsMatcher()
        hashedCredentialsMatcher.hashAlgorithmName = "md5"
        hashedCredentialsMatcher.hashIterations = 2
        credentialsMatcher=hashedCredentialsMatcher
    }

    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo? {
        log.info("授权：$token")
        val userToken = token as UsernamePasswordToken
        val userDO = userService.getUserByUserName(userToken.username)
        val passwordFromDB: String = userDO?.password ?: ""
        val passwordMatcher = getPasswordMatcher(passwordFromDB)
        return userDO?.let {
            val loginUser = LoginUser(it.id.toHexString(), it.userName,it.nickname, it.password)
            //和token中的密码比较
            return SimpleAuthenticationInfo(
                loginUser, passwordMatcher,name
            )
        }
    }

    /**
     * 加密的密码
     */
    private fun getPasswordMatcher(currentPassword: String): String {
        return Md5Hash(currentPassword, null, 2).toString()
    }
    override fun getAuthenticationTokenClass(): Class<*> {
        return UsernamePasswordToken::class.java
    }
}