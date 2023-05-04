package com.fis.crm.service.mapper;

import com.fis.crm.domain.EmailConfig;
import com.fis.crm.service.dto.EmailConfigDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EmailConfigMapper extends EntityMapper<EmailConfigDTO, EmailConfig> {
}
