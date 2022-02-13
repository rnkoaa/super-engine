package com.olive.books

import io.micronaut.context.env.Environment.DEVELOPMENT
import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.olive.books")
//        .defaultEnvironments(DEVELOPMENT)
        .start()
}