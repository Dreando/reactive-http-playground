package com.example.multicastingtest.channel

import com.example.multicastingtest.MetricsBuilder
import com.example.multicastingtest.domain.Event
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.WorkQueueProcessor
import reactor.core.scheduler.Schedulers
import reactor.util.concurrent.WaitStrategy

typealias ChannelId = String
typealias SubscriberId = String

class Channel(private val channelId: ChannelId,
              private val accessToken: AccessToken,
              metricsBuilder: MetricsBuilder) {

    private val log = LoggerFactory.getLogger(Channel::class.java)

    private val channelCounter = metricsBuilder.counter(channelId)

    private val channelProcessor: WorkQueueProcessor<Event> = WorkQueueProcessor.builder<Event>()
            .bufferSize(256)
            .share(true)
            .waitStrategy(WaitStrategy.parking())
            .autoCancel(false)
            .build()

    private val subscription = channelProcessor.subscribeOn(Schedulers.newElastic("pool-${this.channelId}"))

    fun publish(event: Event, someAccessToken: AccessToken) {
        if (someAccessToken.matches(this.accessToken)) {
            channelProcessor.onNext(event)
            channelCounter.increment()
            log.info("Publishing event '$event' on channel $channelId")
            return
        }
        throw IllegalStateException("Can't publish, wrong access token!")
    }

    fun subscribe(subscriberId: SubscriberId): Flux<Event> {
        log.info("Subscribing $subscriberId to channel $channelId")
        return subscription
    }

    fun toReadOnly() = ReadOnlyChannel(this.channelId)
}