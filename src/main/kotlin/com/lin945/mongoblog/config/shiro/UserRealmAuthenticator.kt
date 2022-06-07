package com.lin945.mongoblog.config.shiro

import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.UnknownAccountException
import org.apache.shiro.authc.pam.ModularRealmAuthenticator
import org.apache.shiro.authc.pam.ShortCircuitIterationException
import org.apache.shiro.realm.Realm
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserRealmAuthenticator: ModularRealmAuthenticator() {
    val log : Logger = LoggerFactory.getLogger(this.javaClass)
    override fun doMultiRealmAuthentication(
        realms: MutableCollection<Realm>?,
        token: AuthenticationToken?
    ): AuthenticationInfo {
        val strategy = authenticationStrategy

        var aggregate = strategy.beforeAllAttempts(realms, token)
        var ex:Throwable?=null
        if (log.isTraceEnabled) {
            log.trace("Iterating through {} realms for PAM authentication", realms!!.size)
        }

        for (realm in realms!!) {
            aggregate = try {
                strategy.beforeAttempt(realm, token, aggregate)
            } catch (shortCircuitSignal: ShortCircuitIterationException) {
                // Break from continuing with subsequnet realms on receiving
                // short circuit signal from strategy
                break
            }
            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm)
                var info: AuthenticationInfo? = null
                var t: Throwable? = null
                try {
                    info = realm.getAuthenticationInfo(token)
                } catch (throwable: Throwable) {
                    t = throwable
                    ex=throwable
                    if (log.isDebugEnabled) {
                        val msg =
                            "Realm [$realm] threw an exception during a multi-realm authentication attempt:"
                        log.debug(msg, t)
                    }
                }
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t)
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token)
            }
        }
        /**
         * 手动抛出异常z
         */
        ex?.run {
            throw this
        }
        aggregate = strategy.afterAllAttempts(token, aggregate)

        return aggregate
    }
}