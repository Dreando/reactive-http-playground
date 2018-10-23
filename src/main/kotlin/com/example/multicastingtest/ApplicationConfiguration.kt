package com.example.multicastingtest

import com.example.multicastingtest.app.routingBeans
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans

fun beans(): BeanDefinitionDsl {
    return beans {
        routingBeans()
    }
}