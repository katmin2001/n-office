package com.fis.crm.service.mapper;

import com.fis.crm.domain.ConfigAutoEmail;
import com.fis.crm.service.dto.ConfigAutoEmailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ConfigAutoEmailMapper extends EntityMapper<ConfigAutoEmailDTO, ConfigAutoEmail> {
}
