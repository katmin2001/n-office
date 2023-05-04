package com.fis.crm.service.mapper;

import com.fis.crm.domain.CallInfo;
import com.fis.crm.service.dto.CallInfoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallInfoMapper extends EntityMapper<CallInfoDTO, CallInfo> {
}
