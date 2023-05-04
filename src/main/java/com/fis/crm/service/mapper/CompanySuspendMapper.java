package com.fis.crm.service.mapper;

import com.fis.crm.domain.CompanySuspend;
import com.fis.crm.service.dto.CompanySuspendDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CompanySuspendMapper extends EntityMapper<CompanySuspendDTO, CompanySuspend> {
}
