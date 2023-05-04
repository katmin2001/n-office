package com.fis.crm.service.mapper;

import com.fis.crm.domain.ProccessTicketAttachment;
import com.fis.crm.service.dto.ProcessTicketAttachmentDTO;
import com.fis.crm.domain.ConfirmTicketAttachment;
import com.fis.crm.service.dto.ConfirmTicketAttachmentDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ConfirmTicketAttachment} and its DTO {@link ConfirmTicketAttachmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfirmTicketAttachmentMapper extends EntityMapper<ConfirmTicketAttachmentDTO, ConfirmTicketAttachment> {}
