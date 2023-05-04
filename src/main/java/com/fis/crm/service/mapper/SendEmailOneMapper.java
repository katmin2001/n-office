package com.fis.crm.service.mapper;

import com.fis.crm.domain.SendEmailOne;
import com.fis.crm.service.dto.SendEmailOneDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SendEmailOneMapper extends EntityMapper<SendEmailOneDTO, SendEmailOne> {
}
