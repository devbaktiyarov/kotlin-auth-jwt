package com.devbaktiyarov.kotlin.authjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthJwtApplication

fun main(args: Array<String>) {
	runApplication<AuthJwtApplication>(*args)
}
