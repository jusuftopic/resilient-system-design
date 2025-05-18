package org.example.eventproducer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.example.eventproducer.entity.enums.PaymentStatus;

/**
 * Represents a payment entity.
 * This entity is used to store payment information in the database.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentReference;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String paymentMethod;

    private String description;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

    @PrePersist
    private void prePersist()
    {
        this.createdAt = LocalDateTime.now();
        this.paymentReference = UUID.randomUUID().toString();
        if (this.status == null)
        {
            this.status = PaymentStatus.PENDING;
        }
    }
}
