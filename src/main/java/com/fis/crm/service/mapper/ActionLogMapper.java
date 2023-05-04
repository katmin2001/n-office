package com.fis.crm.service.mapper;


import com.fis.crm.domain.ActionLog;
import com.fis.crm.domain.ConfigSchedule;
import com.fis.crm.service.dto.ActionLogDTO;
import com.fis.crm.service.dto.ConfigScheduleDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ActionLog} and its DTO {@link ActionLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ActionLogMapper extends EntityMapper<ActionLogDTO, ActionLog> {



    default ActionLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActionLog actionLog = new ActionLog();
        actionLog.setId(id);
        return actionLog;
    }
}
