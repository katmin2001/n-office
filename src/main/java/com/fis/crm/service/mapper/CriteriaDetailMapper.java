package com.fis.crm.service.mapper;

import com.fis.crm.domain.CriteriaDetail;
import com.fis.crm.service.dto.CriteriaDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CriteriaDetailMapper extends EntityMapper<CriteriaDetailDTO, CriteriaDetail>{
}
