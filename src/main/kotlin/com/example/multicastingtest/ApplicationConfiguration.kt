package com.example.multicastingtest

import com.example.multicastingtest.app.routingBeans
import com.example.multicastingtest.app.webClient
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

fun beans(): BeanDefinitionDsl {
    return beans {
        routingBeans()
        webClient()
    }
}