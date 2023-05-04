package com.fis.crm.service.mapper;

import com.fis.crm.domain.ContractorsSuspend;
import com.fis.crm.service.dto.ContractorsSuspendDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ContractorsSuspendMapper extends EntityMapper<ContractorsSuspendDTO, ContractorsSuspend> {
}
