package com.devbaktiyarov.kotlin.authjwt.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import javax.sql.DataSource

@Configuration
class JpaConfig(@Autowired val env: Environment){

    @Bean
    fun getDataSource(): DataSource? {
        return DataSourceBuilder.create()
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url(getDataSourceUrl())
            .username(env.getProperty("DB_USERNAME"))
            .password(env.getProperty("DB_PASSWORD"))
            .build()
    }

    private fun getDataSourceUrl(): String? {
        return ("jdbc:mysql://"
                + env.getProperty("DB_HOST") + "/"
                + env.getProperty("DB_NAME"))
    }
}