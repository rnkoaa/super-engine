package com.olive.booksanalytics

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.olive.books")
        .start()
}