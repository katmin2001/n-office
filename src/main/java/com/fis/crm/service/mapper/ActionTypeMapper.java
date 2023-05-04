package com.fis.crm.service.mapper;


import com.fis.crm.domain.ActionLog;
import com.fis.crm.domain.ActionType;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ActionTypeDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ActionLog} and its DTO {@link ActionLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActionTypeMapper extends EntityMapper<ActionTypeDTO, ActionType> {
}
