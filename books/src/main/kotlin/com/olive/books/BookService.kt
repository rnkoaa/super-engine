package com.olive.books

import jakarta.annotation.PostConstruct
import jakarta.inject.Singleton
import java.util.*


@Singleton
class BookService {

    private val bookStore: MutableList<Book> = mutableListOf()

    @PostConstruct
    fun init() {
        bookStore.add(Book("1491950358", "Building Microservices"))
        bookStore.add(Book("1680502395", "Release It!"))
        bookStore.add(Book("0321601912", "Continuous Delivery"))
    }

    fun listAll(): List<Book> = bookStore

    fun findByIsbn(isbn: String): Optional<Book> =
        bookStore.stream()
            .filter { (i) -> i == isbn }
            .findFirst()
}