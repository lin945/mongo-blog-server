package com.lin945.mongoblog.config.shiro

import com.fasterxml.jackson.databind.ObjectMapper
import com.lin945.mongoblog.pojo.CodeConfig
import com.lin945.mongoblog.pojo.Result
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 解决options请求问题
 */
class JwtFilter : BearerHttpAuthenticationFilter() {
    val objectMapper: ObjectMapper = ObjectMapper()
    val log : Logger = LoggerFactory.getLogger(this.javaClass)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        var loggedIn = false //false by default or we wouldn't be in this method
        /**
         * 尝试登录
         */
        if (isLoginAttempt(request, response)) {
            loggedIn = executeLogin(request, response)
        }
        if (!loggedIn) {
            val req = request as HttpServletRequest
            val resp = response as HttpServletResponse
            if (req.method == HttpMethod.OPTIONS.name) {
                resp.status = HttpStatus.OK.value()
            }
            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.contentType = "application/json; charset=utf-8";
            resp.characterEncoding = "UTF-8";
            resp.writer.write(objectMapper.writeValueAsString(Result.fail(CodeConfig.TOKEN_NOTNULL)))
            return false
        }
        return loggedIn
    }
}