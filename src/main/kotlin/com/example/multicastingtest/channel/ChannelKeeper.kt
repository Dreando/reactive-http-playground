package com.example.multicastingtest.channel

import com.example.multicastingtest.MetricsBuilder
import com.example.multicastingtest.domain.Event
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.*

@Component
class ChannelKeeper(private val metricsBuilder: MetricsBuilder) {

    private final val log = LoggerFactory.getLogger(ChannelKeeper::class.java)

    private final val channels = mutableMapOf<ChannelId, Channel>()

    private final val channelsProcessor = ReplayProcessor.create<ReadOnlyChannel>(256)

    fun createChannel(channelId: ChannelId): AccessToken {
        if (channels[channelId] != null) {
            throw IllegalArgumentException("A channel with provided name already exists")
        }
        return AccessToken.random().also { newToken ->
            log.info("Creating channel of ID: {} and accessToken: {}", channelId, newToken)
            channels[channelId] = Channel(channelId, newToken, metricsBuilder).also { newChannel ->
                channelsProcessor.onNext(newChannel.toReadOnly())
            }
        }
    }

    fun getAllChannels(): Flux<ReadOnlyChannel> {
        return channelsProcessor.doOnSubscribe {
            log.info("Streaming all the channels")
        }
    }

    fun subscribe(channelId: ChannelId, subscriberId: SubscriberId): Flux<Event> {
        return channels[channelId]?.subscribe(subscriberId)
                ?: throw IllegalArgumentException("No channel found for name $channelId")
    }

    fun publish(channelId: ChannelId, accessToken: AccessToken, event: Mono<Event>) {
        channels[channelId]?.let { channel ->
            event.subscribe { event ->
                channel.publish(event, accessToken)
            }
        } ?: throw IllegalArgumentException("Can't publish to non existent channel!")
    }
}