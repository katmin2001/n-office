package com.fis.crm.service.mapper;

import com.fis.crm.domain.CriteriaRating;
import com.fis.crm.service.dto.CriteriaRatingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {} )
public interface CriteriaRatingMapper extends EntityMapper<CriteriaRatingDTO, CriteriaRating>{
}
