package com.fis.crm.repository;

import com.fis.crm.domain.ProccessTicket;
import com.fis.crm.domain.ProccessTicketAttachment;
import com.fis.crm.domain.TicketRequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.List;

/**
 * Spring Data SQL repository for the TicketRequestAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessTicketAttachmentRepository extends JpaRepository<ProccessTicketAttachment, Long> {
    List<ProccessTicketAttachment> findAllByTicketRequestId(Long ticketRequestId);

}
