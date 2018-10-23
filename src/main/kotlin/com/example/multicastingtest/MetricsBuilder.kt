package com.example.multicastingtest

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component


@Component
class MetricsBuilder(private val meterRegistry: MeterRegistry) {

    fun counter(counterName: String): Counter {
        return meterRegistry.counter(counterName)
    }
}