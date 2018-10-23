package com.example.multicastingtest.channel

import com.example.multicastingtest.domain.MapStateEvent
import com.example.multicastingtest.sse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

const val channelIdVariable = "channelId"
const val accessTokenVariable = "accessToken"
const val subscriberIdVariable = "subscriberId"

@Component
class ChannelHandler(private val channelKeeper: ChannelKeeper) {

    fun createChannel(req: ServerRequest): Mono<ServerResponse> {
        return try {
            channelKeeper.createChannel(req.pathVariable(channelIdVariable)).let { accessToken ->
                ServerResponse.ok().syncBody(accessToken)
            }
        } catch (exception: Exception) {
            ServerResponse.badRequest().syncBody(exception.localizedMessage)
        }
    }

    fun getAllChannels(req: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().syncBody(channelKeeper.getAllChannels())
    }

    fun subscribe(req: ServerRequest): Mono<ServerResponse> {
        return try {
            sse(channelKeeper.subscribe(req.pathVariable(channelIdVariable), req.pathVariable(subscriberIdVariable)))
        } catch (exception: Exception) {
            return ServerResponse.noContent().build()
        }
    }

    fun publish(req: ServerRequest): Mono<ServerResponse> {
        fun String.toAccessToken() = AccessToken(this)
        return try {
            channelKeeper.publish(
                    req.pathVariable(channelIdVariable),
                    req.pathVariable(accessTokenVariable).toAccessToken(),
                    req.bodyToMono(MapStateEvent::class.java))
            ServerResponse.ok().build()
        } catch (exception: Exception) {
            ServerResponse.badRequest().syncBody(exception.localizedMessage)
        }
    }
}