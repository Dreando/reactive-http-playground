package com.example.multicastingtest

import org.slf4j.LoggerFactory
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ApplicationRunner

fun main(args: Array<String>) {

    val log = LoggerFactory.getLogger(ApplicationRunner::class.java)

    val springApplication = SpringApplicationBuilder()
            .initializers(beans())
            .web(WebApplicationType.REACTIVE)
            .sources(ApplicationRunner::class.java)
            .build(*args)

    val context = springApplication.run()
}
