package com.example.multicastingtest

import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ApplicationRunner

fun main(args: Array<String>) {

    val springApplication = SpringApplicationBuilder()
            .initializers(beans())
            .web(WebApplicationType.REACTIVE)
            .sources(ApplicationRunner::class.java)
            .build(*args)

    springApplication.run()
}
