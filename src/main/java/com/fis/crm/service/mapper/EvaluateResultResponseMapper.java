package com.fis.crm.service.mapper;


import com.fis.crm.domain.*;
import com.fis.crm.service.dto.EvaluateResultResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluateResultResponse} and its DTO {@link EvaluateResultResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EvaluateResultResponseMapper extends EntityMapper<EvaluateResultResponseDTO, EvaluateResultResponse> {



    default EvaluateResultResponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        EvaluateResultResponse evaluateResultResponse = new EvaluateResultResponse();
        evaluateResultResponse.setId(id);
        return evaluateResultResponse;
    }
}
