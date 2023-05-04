package com.fis.crm.service.mapper;

import com.fis.crm.domain.ProccessTicket;
import com.fis.crm.domain.ProccessTicketAttachment;
import com.fis.crm.service.dto.ProcessTicketDTO;
import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ProccessTicketAttachment} and its DTO {@link ProcessTicketAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessTicketAttachmentMapper extends EntityMapper<ProcessTicketAttachmentDTO, ProccessTicketAttachment> {}
