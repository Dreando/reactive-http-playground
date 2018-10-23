package com.example.multicastingtest

import com.example.multicastingtest.channel.ChannelId
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component


@Component
class MetricsBuilder(private val meterRegistry: MeterRegistry) {

    fun counter(counterName: String, channelId: ChannelId): Counter {
        return meterRegistry.counter(counterName, channelId)
    }
}