package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.ProcessTicketEntityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessTicketEntity} and its DTO {@link ProcessTicketEntityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessTicketEntityMapper extends EntityMapper<ProcessTicketEntityDTO, ProcessTicketEntity> {



    default ProcessTicketEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcessTicketEntity processTicketEntity = new ProcessTicketEntity();
        processTicketEntity.setId(id);
        return processTicketEntity;
    }
}
