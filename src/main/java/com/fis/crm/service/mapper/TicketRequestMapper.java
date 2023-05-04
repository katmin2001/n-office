package com.fis.crm.service.mapper;

import com.fis.crm.domain.*;
import com.fis.crm.service.dto.TicketRequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketRequest} and its DTO {@link TicketRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketRequestMapper extends EntityMapper<TicketRequestDTO, TicketRequest> {}
