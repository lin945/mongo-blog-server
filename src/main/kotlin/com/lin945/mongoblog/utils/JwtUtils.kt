@file:JvmName("JwtUtils")

package com.lin945.mongoblog.utils

import com.lin945.mongoblog.pojo.LoginUser
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.ExpiredCredentialsException
import org.apache.shiro.authc.pam.UnsupportedTokenException
import java.util.*

const val SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"
private const val secret = "f4e2e52034348f86b67cde581c0f9eb5"
private const val expire: Long = 604800
private const val header = "Authorization"
//hs256的key
private val key=Keys.hmacShaKeyFor(secret.toByteArray())!!

/**
 * 生成jwt token
 */
fun generateToken(user: LoginUser): String {
    val nowDate = Date()
    //过期时间
    val expireDate = Date(nowDate.time + expire * 1000)
    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(user.id)
        .claim("userName", user.userName)
        .claim("nickName", user.nickName)
        .setIssuedAt(nowDate)
        .setExpiration(expireDate)
        .signWith( key,SignatureAlgorithm.HS256)
        .compact()
}

/**
 * 解析jwt
 */
fun getLoginUser(token: String?): LoginUser? {
    return try {
        Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token)
            .body?.let {
                LoginUser(it.subject, it["userName"].toString(), it["nickName"].toString(), "")
            }
    } catch (e: Exception) {
        if (e is ExpiredJwtException) {
            throw ExpiredCredentialsException(e.message)
        }else if (e is SignatureException){
            throw UnsupportedTokenException(e.message)
        }
        e.printStackTrace()
        return null
    }
}
fun getLoginUser()= SecurityUtils.getSubject().principal as LoginUser;
/**
 * 校验jwt
 */
fun verify(token: String?): Boolean {
    return token?.let { Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(it) != null } ?: false
}

/**
 * token是否过期
 * @return  true：过期
 */
fun isTokenExpired(expiration: Date): Boolean {
    return expiration.before(Date())
}

public fun RandomId(): String {
    val random = Random()
    var Random_generation_id = ""
    for (i in 1..32) {
        val letterORnumber = if (random.nextInt(2) % 2 == 0) "letter" else "number"
        if (letterORnumber.equals("letter", ignoreCase = true)) {
            val Random_num = if (random.nextInt(2) % 2 == 0) 65 else 97
            Random_generation_id += (Random_num + random.nextInt(26)).toChar()
        } else if (letterORnumber.equals("number", ignoreCase = true)) {
            Random_generation_id += random.nextInt(10).toString()
        }
    }
    //	System.out.println(Random_generation_id);
    return Random_generation_id
}