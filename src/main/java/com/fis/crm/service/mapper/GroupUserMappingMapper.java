package com.fis.crm.service.mapper;

import com.fis.crm.domain.GroupUserMapping;
import com.fis.crm.service.dto.GroupUserMappingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface GroupUserMappingMapper extends EntityMapper<GroupUserMappingDTO, GroupUserMapping> {
}
