package org.richard.event;

import java.time.Instant;

public record RetryPolicy(
    int maxRetry,
    int retryCounter,
    Instant nextRetry
) {}
