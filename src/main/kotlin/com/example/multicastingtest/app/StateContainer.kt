package com.example.multicastingtest.app

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.ReplayProcessor

@Component
class StateContainer {

    private final val log = LoggerFactory.getLogger(StateContainer::class.java)

    private final val processor: ReplayProcessor<Event> = ReplayProcessor.create()

    init {
        processor.onNext(MapStateEvent(centerPoint = Point(51.7732033,19.4105531), zoom = 17.0))
    }

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