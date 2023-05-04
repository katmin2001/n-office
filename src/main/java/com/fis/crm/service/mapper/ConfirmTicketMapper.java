package com.fis.crm.service.mapper;

import com.fis.crm.domain.ProccessTicket;
import com.fis.crm.domain.ConfirmTicket;
import com.fis.crm.service.dto.ProcessTicketDTO;
import com.fis.crm.service.dto.ConfirmTicketDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ConfirmTicket} and its DTO {@link ConfirmTicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfirmTicketMapper extends EntityMapper<ConfirmTicketDTO, ConfirmTicket> {}
