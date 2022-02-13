package com.olive.booksanalytics

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@MicronautTest
class AnalyticsServiceTest {

    @Inject
    lateinit var analyticsService: AnalyticsService

    @Test
    fun testUpdateBookAnalyticsAndGetAnalytics() {
        val b1 = Book("1491950358", "Building Microservices")
        val b2 = Book("1680502395", "Release It!")

        analyticsService.updateBookAnalytics(b1)
        analyticsService.updateBookAnalytics(b1)
        analyticsService.updateBookAnalytics(b1)
        analyticsService.updateBookAnalytics(b2)

        val analytics = analyticsService.listAnalytics()

        assertEquals(2, analytics.size)
        assertEquals(3, findBookAnalytics(b1, analytics).count)
        assertEquals(1, findBookAnalytics(b2, analytics).count)
    }

    private fun findBookAnalytics(b: Book, analytics: List<BookAnalytics>): BookAnalytics {
        val ba: BookAnalytics? = analytics.filter { (bookIsbn) -> bookIsbn == b.isbn }.firstOrNull()
        return ba ?: throw RuntimeException("Book not found")
    }
}
