package org.example.eventproducer.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a payment request.
 */
@Getter
@Builder
public class PaymentRequestDTO
{
    private BigDecimal amount;
    private String currency;
    private String paymentMethod;
    private String description;
    private Long invoiceId;
}
