package com.example.multicastingtest.routing

import com.example.multicastingtest.channel.ChannelHandler
import com.example.multicastingtest.channel.accessTokenVariable
import com.example.multicastingtest.channel.channelIdVariable
import com.example.multicastingtest.channel.subscriberIdVariable
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.function.server.router

fun BeanDefinitionDsl.routingBeans() {
    bean("router") {
        router {
            ("/").nest {
                "/channel".nest {
                    POST("/{$channelIdVariable}", ref<ChannelHandler>()::createChannel)
                    POST("/{$channelIdVariable}/accessToken/{$accessTokenVariable}", ref<ChannelHandler>()::publish)
                    GET("/{$channelIdVariable}/subscriberId/{$subscriberIdVariable}", ref<ChannelHandler>()::subscribe)
                    GET("/", ref<ChannelHandler>()::getAllChannels)
                }
            }
        }
    }
}