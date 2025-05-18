package org.example.eventproducer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import org.example.eventproducer.entity.enums.PaymentOutboxStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an outbox entry for payment events.
 * This entity is used to store events that need to be sent to a message broker.
 * The outbox pattern is used to ensure that events are reliably sent even in the case of failures.
 */
@Entity
@Table(name = "payment_outbox")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOutbox
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID eventId;

    @Column(nullable = false)
    private String aggregateType = "Payment";

    @Column(nullable = false)
    private String aggregateId; // e.g. "payment reference"

    @Column(nullable = false)
    private String eventType; // e.g., "PaymentCreated", "PaymentCompleted"

    @Lob
    @Column(nullable = false)
    private String payload; // JSON representation of the event

    @Column(nullable = false)
    private LocalDateTime occurredAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentOutboxStatus status; // e.g., "PENDING", "SENT", "FAILED"

    private LocalDateTime processedAt;

    @PrePersist
    public void prePersist() {
        this.eventId = UUID.randomUUID();
        this.occurredAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = PaymentOutboxStatus.PENDING;
        }
    }

}
