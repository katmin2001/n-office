package com.fis.crm.service.mapper;

import com.fis.crm.domain.EmailConfigHistory;
import com.fis.crm.service.dto.EmailConfigHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EmailConfigHistoryMapper extends EntityMapper<EmailConfigHistoryDTO, EmailConfigHistory>{
}
