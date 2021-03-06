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
        * filter????????????????????????
        * http://shiro.apache.org/web.html#urls-
        * ????????????????????????
        * https://shiro.apache.org/web.html#default-filters
        */
        val filterRuleMap: MutableMap<String, String> = HashMap()
        filterRuleMap["/blog/*"] = "anon"
        filterRuleMap["/comment/*"] = "anon"
//        filterRuleMap["/error"] = "anon"
//        filterRuleMap["/register"] = "anon"
        filterRuleMap["/admin/login"] = "anon"
        //??????????????????????????????????????????

        // ???????????????jwt??????????????????
        //??? ????????????shiro1.8???????????????????????????authcBearer-BearerHttpAuthenticationFilter???
        // jwt??????????????????????????????filter????????????????????????????????????????????????????????????????????????
        //filterRuleMap["/**"] = "authcBearer"
        filterRuleMap["/**"] = "authc"
        factoryBean.filters["authc"]= JwtFilter()
        //??? ?????????????????????????????????/**???????????????????????????????????????????????????????????????
        factoryBean.setGlobalFilters(listOf("noSessionCreation"))
        //??? ?????????????????????NoSessionCreationFilter?????????????????????????????????????????????
        factoryBean.securityManager = securityManager
        factoryBean.filterChainDefinitionMap = filterRuleMap
        return factoryBean
    }



}