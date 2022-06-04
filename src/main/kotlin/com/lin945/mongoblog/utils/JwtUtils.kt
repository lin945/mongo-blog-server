@file:JvmName("JwtUtils")

package com.lin945.mongoblog.utils

import com.lin945.mongoblog.pojo.LoginUser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*


private const val secret = "f4e2e52034348f86b67cde581c0f9eb5"
private const val expire: Long = 604800
private const val header = "Authorization"
private val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)!!

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
        .signWith(key)
        .compact()
}

fun getLoginUser(token: String?): LoginUser? {
    return try {
        Jwts.parserBuilder().setSigningKey(key).build()
            .parseClaimsJws(token)
            .body?.let {
                LoginUser(it.subject, it["userName"].toString(), it["nickname"].toString(), "")
            }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}


fun verify(token: String?): Boolean {
    return token?.let { Jwts.parserBuilder().setSigningKey(key).build().parsePlaintextJws(it) != null } ?: false
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