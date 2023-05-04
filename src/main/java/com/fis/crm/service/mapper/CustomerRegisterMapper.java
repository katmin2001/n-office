package com.fis.crm.service.mapper;

import com.fis.crm.domain.CustomerRegister;
import com.fis.crm.service.dto.CustomerRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerRegisterMapper extends EntityMapper<CustomerRegisterDTO, CustomerRegister> {
}
