package com.olive.books

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micronaut.scheduling.annotation.Scheduled
import java.util.*
import java.util.concurrent.atomic.*

class Scheduler(private val meterRegistry: MeterRegistry) {
    private lateinit var testGauge: AtomicInteger
    private lateinit var testCounter: Counter

    //    @Scheduled(fixedRateString = "1000", initialDelayString = "0")
    @Scheduled(fixedDelay = "45s", initialDelay = "5s")
    fun count() {
        testGauge.set(getRandomNumberInRange(0, 100));
        testCounter.increment();
    }

    private fun getRandomNumberInRange(min: Int, max: Int): Int {
        require(min < max) { "max must be greater than min" }
        val r = Random()
        return r.nextInt(max - min + 1) + min
    }
}