package org.example.eventconsumer.repository;

import org.example.eventconsumer.entity.InvoiceInbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceInboxRepository extends JpaRepository<InvoiceInbox, Long>
{
}
