package com.fis.crm.service.mapper;

import com.fis.crm.domain.Ticket;
import com.fis.crm.service.dto.TicketDTO;
import com.fis.crm.domain.Customer;
import com.fis.crm.service.dto.CustomerDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {}
