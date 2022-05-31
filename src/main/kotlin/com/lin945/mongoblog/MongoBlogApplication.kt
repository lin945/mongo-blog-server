package com.lin945.mongoblog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoBlogApplication

fun main(args: Array<String>) {
    runApplication<MongoBlogApplication>(*args)
}
