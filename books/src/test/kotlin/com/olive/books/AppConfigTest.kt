package com.olive.books

import io.micronaut.context.ApplicationContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AppConfigTest {

    @Test
    fun testAppConfig() {
        val items: MutableMap<String, Any> = HashMap()
        items["app.name"] = "green"
        items["app.db.username"] = "evolution"

        val ctx = ApplicationContext.run(items)
        val appConfig = ctx.getBean(AppConfig::class.java)
        val dbConfig = appConfig.builder.build()
        Assertions.assertEquals("evolution", dbConfig.username)
        Assertions.assertEquals("green", appConfig.name)

        ctx.close()
    }
}