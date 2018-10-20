package com.example.multicastingtest.app

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.router

fun BeanDefinitionDsl.routingBeans() {
    bean("router") {
        router {
            ("/events" and accept(MediaType.APPLICATION_JSON_UTF8)).nest {

                GET("/", ref<Handler>()::streamEvents)
                POST("/", ref<Handler>()::pushEvent)
            }
        }
    }
}

fun BeanDefinitionDsl.webClient() {
    bean("webClient") {
        WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build()
    }
}