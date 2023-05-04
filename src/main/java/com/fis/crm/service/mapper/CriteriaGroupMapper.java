package com.fis.crm.service.mapper;

import com.fis.crm.domain.CriteriaGroup;
import com.fis.crm.service.dto.CriteriaGroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CriteriaGroupMapper extends EntityMapper<CriteriaGroupDTO, CriteriaGroup>{
}
