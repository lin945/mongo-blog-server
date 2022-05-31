package com.lin945.mongoblog.config
import org.apache.shiro.mgt.SecurityManager
import org.apache.shiro.authz.Authorizer
import org.apache.shiro.authz.ModularRealmAuthorizer
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import kotlin.collections.HashMap


@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun corsFilter(): CorsFilter {
        return CorsFilter(UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                addAllowedOrigin("*")
                addAllowedHeader("*")
                addAllowedMethod("*")
            })
        })
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)

    }

    @Bean
    fun shiroFilterFactoryBean(securityManager: SecurityManager): ShiroFilterFactoryBean {
        val factoryBean = ShiroFilterFactoryBean()

        /*
        * filter配置规则参考官网
        * http://shiro.apache.org/web.html#urls-
        * 默认过滤器对照表
        * https://shiro.apache.org/web.html#default-filters
        */
        val filterRuleMap: MutableMap<String, String> = HashMap()
        filterRuleMap["/blog/*"] = "anon"
        filterRuleMap["/comment/*"] = "anon"
//        filterRuleMap["/error"] = "anon"
//        filterRuleMap["/register"] = "anon"
        filterRuleMap["/login"] = "anon"
        //↑配置不参与验证的映射路径。

        // 关键：配置jwt验证过滤器。
        //↓ 此处即为shiro1.8新增的默认过滤器：authcBearer-BearerHttpAuthenticationFilter。jwt验证的很多操作都由该filter自动完成，以致我们只需理解其机制而无需亲手实现。
        filterRuleMap["/**"] = "authcBearer"
        //↑ 如果有其他过滤法则配在/**上，则在第二个参数的字符串里使用逗号间隔。
        factoryBean.setGlobalFilters(listOf("noSessionCreation"))
        //↑ 关键：全局配置NoSessionCreationFilter，把整个项目切换成无状态服务。
        factoryBean.securityManager = securityManager
        factoryBean.filterChainDefinitionMap = filterRuleMap
        return factoryBean
    }

    @Bean
    protected fun authorizer(): Authorizer {
        return ModularRealmAuthorizer()
    }
}