package com.example.multicastingtest

import com.example.multicastingtest.routing.routingBeans
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

fun beans(): BeanDefinitionDsl {
    return beans {
        routingBeans()
    }
}