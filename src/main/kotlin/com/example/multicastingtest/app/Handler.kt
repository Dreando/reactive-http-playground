package com.example.multicastingtest.app

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class Handler(private val stateContainer: StateContainer) {

    fun streamEvents(serverRequest: ServerRequest): Mono<ServerResponse> {
        return sse(stateContainer.listen())
    }

    fun pushEvent(serverRequest: ServerRequest): Mono<ServerResponse> {
        stateContainer.push(serverRequest.bodyToMono(Event::class.java))
        return ServerResponse.ok().build()
    }

}