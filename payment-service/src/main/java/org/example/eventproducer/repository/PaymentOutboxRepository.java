package org.example.eventproducer.repository;

import java.util.List;

import org.example.eventproducer.entity.PaymentOutbox;
import org.example.eventproducer.entity.enums.PaymentOutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing PaymentOutbox entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface PaymentOutboxRepository extends JpaRepository<PaymentOutbox, Long>
{

    /**
     * Finds all PaymentOutbox entities by their status.
     *
     * @param status the status of the PaymentOutbox entities to find
     * @return a list of PaymentOutbox entities with the specified status
     */
    List<PaymentOutbox> findAllByStatus(PaymentOutboxStatus status);
}
