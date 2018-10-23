package com.example.multicastingtest.app

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.function.server.router

fun BeanDefinitionDsl.routingBeans() {
    bean("router") {
        router {
            ("/").nest {
                GET("/subscribe", ref<Handler>()::streamEvents)
                POST("/publish", ref<Handler>()::pushEvent)
            }
        }
    }
}