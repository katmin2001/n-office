package com.fis.crm.repository;

import com.fis.crm.domain.TicketRequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the TicketRequestAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketRequestAttachmentRepository extends JpaRepository<TicketRequestAttachment, Long> {
    List<TicketRequestAttachment> findAllByTicketRequestId(Long ticketRequestId);
}
