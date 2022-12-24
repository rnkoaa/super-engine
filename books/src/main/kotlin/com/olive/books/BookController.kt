package com.olive.books

import io.micrometer.core.instrument.MeterRegistry
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.Optional

@Controller("/books")
class BookController(
    private val bookService: BookService,
    private val meterRegistry: MeterRegistry
) {

    @Get
    fun listAll(): List<Book> {
        meterRegistry
            .counter("web.access", "controller", "book", "action", "list")
            .increment();
        return bookService.listAll()
    }

    @Get("/{isbn}")
    fun findBook(isbn: String): Optional<Book> = bookService.findByIsbn(isbn)
}
