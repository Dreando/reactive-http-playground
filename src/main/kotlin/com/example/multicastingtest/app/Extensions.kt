package com.example.multicastingtest.app

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import reactor.core.publisher.Flux

inline fun <reified T : Any> sse(publisher: Flux<T>) = ServerResponse.ok().bodyToServerSentEvents(publisher)