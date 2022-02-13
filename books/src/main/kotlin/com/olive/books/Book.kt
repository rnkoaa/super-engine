package com.olive.books

import io.micronaut.core.annotation.Introspected

@Introspected
data class Book(val isbn: String, val name: String)