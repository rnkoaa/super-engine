package com.olive.books

import io.micronaut.context.annotation.ConfigurationBuilder
import io.micronaut.context.annotation.ConfigurationProperties

class DbConfigBuilder private constructor(
    val username: String?,
//    val coach: String?,
//    val president: String?
    ) {
    data class Builder(
        var username: String? = null,
//        var coach: String? = null,
//        var president: String? = null
    ) {
        fun withUsername(username: String) = apply { this.username = username }
//        fun withCoach(coach: String) = apply { this.coach = coach }
//        fun withPresident(president: String) = apply { this.president = president }
        fun build() = DbConfigBuilder(username/*, coach, president*/)
    }
}

@ConfigurationProperties("app")
class AppConfig  {
    var name: String? = null
//    var color: String? = null
//    var playerNames: List<String>? = null
    //end::teamConfigClassNoBuilder[]
//tag::teamConfigClassBuilder[]
    @ConfigurationBuilder(prefixes = ["with"], configurationPrefix = "db")
    var builder = DbConfigBuilder.Builder()
//end::teamConfigClassBuilder[]
}