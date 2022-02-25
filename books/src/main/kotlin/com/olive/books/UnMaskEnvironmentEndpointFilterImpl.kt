package com.olive.books

import io.micronaut.management.endpoint.env.EnvironmentEndpointFilter
import io.micronaut.management.endpoint.env.EnvironmentFilterSpecification
import jakarta.inject.Singleton

//@Singleton
class UnMaskEnvironmentEndpointFilterImpl: EnvironmentEndpointFilter {
    override fun specifyFiltering(specification: EnvironmentFilterSpecification?) {
//        specification?.maskNone()
    }
}