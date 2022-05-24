package com.devbaktiyarov.kotlin.authjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class AuthJwtApplication

fun main(args: Array<String>) {

    runApplication<AuthJwtApplication>(*args)
}
