package com.fis.crm.service.mapper;

import com.fis.crm.domain.ConfigAutoSMS;
import com.fis.crm.service.dto.ConfigAutoSMSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ConfigAutoSMSMapper extends EntityMapper<ConfigAutoSMSDTO, ConfigAutoSMS> {
}
