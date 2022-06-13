package com.lin945.mongoblog.config
import com.lin945.mongoblog.config.shiro.JwtFilter
import com.lin945.mongoblog.config.shiro.UserRealmAuthenticator
import org.apache.shiro.realm.Realm
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@AutoConfigureBefore(ShiroWebAutoConfiguration::class)
class WebConfig : WebMvcConfigurer {
    val log : Logger = LoggerFactory.getLogger(this.javaClass)
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
    fun securityManager(realms: Collection<Realm>,userRealmAuthenticator: UserRealmAuthenticator): DefaultWebSecurityManager {
        val sManager = DefaultWebSecurityManager()
        sManager.authenticator=userRealmAuthenticator
        sManager.realms = realms
        return sManager
    }

    @Bean
    fun shiroFilterFactoryBean(securityManager: DefaultWebSecurityManager): ShiroFilterFactoryBean {

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
        filterRuleMap["/admin/login"] = "anon"
        //↑配置不参与验证的映射路径。

        // 关键：配置jwt验证过滤器。
        //↓ 此处即为shiro1.8新增的默认过滤器：authcBearer-BearerHttpAuthenticationFilter。
        // jwt验证的很多操作都由该filter自动完成，以致我们只需理解其机制而无需亲手实现。
        //filterRuleMap["/**"] = "authcBearer"
        filterRuleMap["/**"] = "authc"
        factoryBean.filters["authc"]= JwtFilter()
        //↑ 如果有其他过滤法则配在/**上，则在第二个参数的字符串里使用逗号间隔。
        factoryBean.setGlobalFilters(listOf("noSessionCreation"))
        //↑ 关键：全局配置NoSessionCreationFilter，把整个项目切换成无状态服务。
        factoryBean.securityManager = securityManager
        factoryBean.filterChainDefinitionMap = filterRuleMap
        return factoryBean
    }



}