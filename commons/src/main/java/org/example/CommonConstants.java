package org.example;

/**
 * Common constants used across the applications.
 */
public final class CommonConstants
{
    public static final String KAFKA_PAYMENT_CREATED_EVENT_TOPIC = "payment-created";
    public static final String KAFKA_PAYMENT_CREATED_EVENT_DLQ_TOPIC = "payment-created-dlq";
    public static final String KAFKA_PAYMENT_CREATED_EVENT_5S_RETRY_TOPIC = "payment-created-retry-5s";

}
