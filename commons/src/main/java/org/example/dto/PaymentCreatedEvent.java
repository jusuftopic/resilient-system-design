package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing a payment created event.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentCreatedEvent
{
    public static final int MAX_RETRIES = 3;

    private String eventId;
    private String paymentReference;
    private Long invoiceId;
    private int retryCount = 0;

    public void incrementRetryCount()
    {
        this.retryCount++;
    }
}
