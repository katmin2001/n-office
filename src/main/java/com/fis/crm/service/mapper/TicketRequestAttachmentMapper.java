package com.fis.crm.service.mapper;

import com.fis.crm.domain.TicketRequest;
import com.fis.crm.domain.TicketRequestAttachment;
import com.fis.crm.service.dto.TicketRequestAttachmentDTO;
import com.fis.crm.service.dto.TicketRequestDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link com.fis.crm.domain.TicketRequestAttachment} and its DTO {@link TicketRequestAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketRequestAttachmentMapper extends EntityMapper<TicketRequestAttachmentDTO, TicketRequestAttachment> {}
