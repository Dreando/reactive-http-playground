package com.example.multicastingtest.routing

import com.example.multicastingtest.channel.ChannelHandler
import com.example.multicastingtest.channel.accessTokenVariable
import com.example.multicastingtest.channel.channelIdVariable
import com.example.multicastingtest.channel.subscriberIdVariable
import com.example.multicastingtest.domain.Handler
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.web.reactive.function.server.router

fun BeanDefinitionDsl.routingBeans() {
    bean("router") {
        router {
            ("/").nest {
//                GET("/subscribe", ref<Handler>()::streamEvents)
//                POST("/publish", ref<Handler>()::pushEvent)

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