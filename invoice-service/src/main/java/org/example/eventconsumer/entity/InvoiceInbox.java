package org.example.eventconsumer.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * InvoiceInbox entity representing an invoice inbox in the system.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice_inbox")
public class InvoiceInbox
{
    @Id
    private String eventId;

    private LocalDateTime processedAt;

    @PrePersist
    public void prePersist() {
        this.processedAt = LocalDateTime.now();
    }

}
