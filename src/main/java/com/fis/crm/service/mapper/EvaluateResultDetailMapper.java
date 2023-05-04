package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.EvaluateResultDetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluateResultDetail} and its DTO {@link EvaluateResultDetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluateResultDetailMapper extends EntityMapper<EvaluateResultDetailDTO, EvaluateResultDetail> {



    default EvaluateResultDetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluateResultDetail evaluateResultDetail = new EvaluateResultDetail();
        evaluateResultDetail.setId(id);
        return evaluateResultDetail;
    }
}
