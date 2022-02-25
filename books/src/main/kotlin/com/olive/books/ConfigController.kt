package com.olive.books

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/configs")
class ConfigController(private val configs: AppConfig) {

    @Get("/")
    fun showConfigs(): AppConfig {
        return configs
    }
}