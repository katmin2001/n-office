package com.fis.crm.service.mapper;

import com.fis.crm.domain.TicketRequestAttachment;
import com.fis.crm.domain.ProccessTicket;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.dto.ProcessTicketDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ProccessTicket} and its DTO {@link ProcessTicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessTicketMapper extends EntityMapper<ProcessTicketDTO, ProccessTicket> {}
