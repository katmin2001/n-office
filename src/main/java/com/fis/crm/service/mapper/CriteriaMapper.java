package com.fis.crm.service.mapper;

import com.fis.crm.domain.Criteria;
import com.fis.crm.service.dto.CriteriaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CriteriaMapper extends EntityMapper<CriteriaDTO, Criteria> {
}
