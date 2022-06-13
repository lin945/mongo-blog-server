package com.lin945.mongoblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.SpringCloudApplication
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringCloudApplication
@EnableCaching
class MongoBlogApplication

fun main(args: Array<String>) {
    runApplication<MongoBlogApplication>(*args)
}
