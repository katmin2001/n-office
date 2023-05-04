package com.fis.crm.repository;

import com.fis.crm.domain.ConfirmTicketAttachment;
import com.fis.crm.domain.ProccessTicketAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the ConfirmTicketAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfirmTicketAttachmentRepository extends JpaRepository<ConfirmTicketAttachment, Long> {
    Optional<List<ConfirmTicketAttachment>> getAllByTicketId(Long ticketId);
}
