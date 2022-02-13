package com.olive.booksanalytics

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment

@Requires(notEnv = [Environment.TEST])
@KafkaListener
class AnalyticsConsumer(private val analyticsService: AnalyticsService) {
    @Topic("analytics")
    fun updateAnalytics(book: Book) = analyticsService.updateBookAnalytics(book)
}