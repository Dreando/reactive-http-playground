package com.example.multicastingtest.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.WorkQueueProcessor
import reactor.util.concurrent.WaitStrategy

@Component
class StateContainer {

    private final val log = LoggerFactory.getLogger(StateContainer::class.java)

    private final val processor: WorkQueueProcessor<Event> = WorkQueueProcessor.builder<Event>()
            .bufferSize(256)
            .share(true)
            .waitStrategy(WaitStrategy.parking())
            .autoCancel(false)
            .build()

    fun push(evt: Mono<Event>) {
        evt.subscribe { event ->
            this.log.info("Broadcasting event {} to the processor", event)
            this.processor.onNext(event)
        }
    }

    fun listen(): Flux<Event> {
        return processor
                .serialize()
                .doOnError { log.error("An error happened, ", it.message) }
                .doOnSubscribe {
            log.info("Got new subscriber")
        }
    }
}