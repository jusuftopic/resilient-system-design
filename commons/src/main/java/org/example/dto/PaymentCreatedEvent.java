package org.example.dto;


import java.math.BigDecimal;

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
    private String paymentReference;
    private BigDecimal amount;
    private String currency;
    private Long invoiceId;
}
