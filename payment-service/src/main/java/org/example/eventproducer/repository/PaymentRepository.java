package org.example.eventproducer.repository;

import org.example.eventproducer.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Payment entity.
 * This interface extends JpaRepository to provide CRUD operations for Payment entities.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
}
