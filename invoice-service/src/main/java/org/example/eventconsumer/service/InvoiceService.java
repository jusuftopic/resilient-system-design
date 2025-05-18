package org.example.eventconsumer.service;

import java.time.LocalDateTime;

import jakarta.transaction.Transactional;

import org.example.dto.PaymentCreatedEvent;
import org.example.eventconsumer.entity.Invoice;
import org.example.eventconsumer.entity.InvoiceInbox;
import org.example.eventconsumer.entity.enums.InvoiceStatus;
import org.example.eventconsumer.repository.InvoiceInboxRepository;
import org.example.eventconsumer.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for handling invoice-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService
{
    private final InvoiceInboxRepository invoiceInboxRepository;
    private final InvoiceRepository invoiceRepository;

    /**
     * Updates the invoice based on the payment created event.
     *
     * @param payload the payment created event payload
     */
    @Transactional
    public void updateInvoice(final PaymentCreatedEvent payload)
    {
        if (invoiceInboxRepository.existsById(payload.getEventId()))
        {
            log.info("Invoice {} in event {} already processed", payload.getInvoiceId(), payload.getEventId());
            return;
        }

        // real-world scenario: get an invoice from DB and update data; here we just simulate it - no invoice is saved
        final Invoice invoice = invoiceRepository.findById(payload.getInvoiceId())
            .orElseThrow(() -> new RuntimeException("Invoice not found for id " + payload.getInvoiceId()));
        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaymentReference(payload.getPaymentReference());
        invoiceRepository.save(invoice);

        // process invoice inbox
        final InvoiceInbox invoiceInbox = new InvoiceInbox();
        invoiceInbox.setEventId(payload.getEventId());
        invoiceInboxRepository.save(invoiceInbox);
    }


}
