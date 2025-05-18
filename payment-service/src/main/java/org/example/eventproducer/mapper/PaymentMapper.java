package org.example.eventproducer.mapper;

import org.example.eventproducer.dto.PaymentRequestDTO;
import org.example.eventproducer.entity.Payment;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting payment-related DTOs to entity objects.
 */
@Component
public class PaymentMapper
{

    /**
     * Maps a PaymentRequestDTO to a Payment entity.
     *
     * @param request the PaymentRequestDTO to map
     * @return the mapped Payment entity
     */
    public Payment map(final PaymentRequestDTO request)
    {
        final Payment entity = new Payment();
        entity.setAmount(request.getAmount());
        entity.setCurrency(request.getCurrency());
        entity.setPaymentMethod(request.getPaymentMethod());
        entity.setDescription(request.getDescription());
        entity.setInvoiceId(request.getInvoiceId());

        return entity;
    }
}
