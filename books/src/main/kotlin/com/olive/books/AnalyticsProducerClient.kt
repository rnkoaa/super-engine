package com.olive.books

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.Topic
import reactor.core.publisher.Mono

@KafkaClient
interface AnalyticsProducerClient {
    @Topic("analytics")
    fun updateAnalytics(book: Book): Mono<Book>
}