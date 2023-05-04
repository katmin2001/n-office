package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.TicketDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {



    default Ticket fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setTicketId(id);
        return ticket;
    }
}
