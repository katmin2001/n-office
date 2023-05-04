package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.ConfigScheduleDTO;

import org.mapstruct.*;
import org.springframework.stereotype.Repository;

/**
 * Mapper for the entity {@link ConfigSchedule} and its DTO {@link ConfigScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigScheduleMapper extends EntityMapper<ConfigScheduleDTO, ConfigSchedule> {



    default ConfigSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConfigSchedule configSchedule = new ConfigSchedule();
        configSchedule.setId(id);
        return configSchedule;
    }
}
