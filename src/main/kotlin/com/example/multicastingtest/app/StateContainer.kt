package com.example.multicastingtest.app

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.DirectProcessor
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class StateContainer {

    private final val log = LoggerFactory.getLogger(StateContainer::class.java)

    private final val processor: DirectProcessor<Event> = DirectProcessor.create()

    fun push(evt: Mono<Event>) {
        evt.subscribe { event ->
            this.log.info("Broadcasting event {} to the processor", event)
            this.processor.onNext(event)
        }
    }

    fun listen(): Flux<Event> {
        return processor.doOnSubscribe {
            this.log.info("Got new subscriber")
        }
    }
}